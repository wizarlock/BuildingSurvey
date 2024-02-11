package com.example.buildingsurvey.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.pdf.PdfRenderer
import android.media.ExifInterface
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.webkit.MimeTypeMap
import androidx.annotation.RequiresApi
import com.example.buildingsurvey.data.model.Audio
import com.example.buildingsurvey.data.model.Drawing
import com.example.buildingsurvey.data.model.Label
import com.example.buildingsurvey.data.model.Project
import com.example.buildingsurvey.ui.screens.AudioAttachment
import dagger.hilt.android.qualifiers.ApplicationContext
import id.zelory.compressor.Compressor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.UUID
import javax.inject.Inject

class Repository @Inject constructor(
    @ApplicationContext private val applicationContext: Context
) : RepositoryInterface {
    private val _projectsList: MutableStateFlow<List<Project>> = MutableStateFlow(listOf())
    private val _audioList: MutableStateFlow<List<Audio>> = MutableStateFlow(listOf())
    private val _drawingsList: MutableStateFlow<List<Drawing>> = MutableStateFlow(listOf())
    private val _labelsList: MutableStateFlow<List<Label>> = MutableStateFlow(listOf())
    private var recorder: MediaRecorder? = null

    override val projectsList = _projectsList.asStateFlow()
    override val drawingsList = _drawingsList.asStateFlow()
    override val audioList = _audioList.asStateFlow()
    override val labelsList = _labelsList.asStateFlow()

    override var currentProject = Project()
    override var currentDrawing = Drawing()
    override var currentLabel = Label()

    private var tempFile: File? = null
    private val outputDir = applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)

    override suspend fun addProject(project: Project, isFileExists: Boolean) {
        if (isFileExists) {
            val name = tempFile!!.name
            val lastDotIndex = name.lastIndexOf(".")
            val fileName = UUID.randomUUID().toString() + name.substring(lastDotIndex)
            project.projectFilePath = "$outputDir/$fileName"
            saveProjectFile(fileName = fileName)
        }
        _projectsList.update { currentList ->
            val updatedList = currentList.toMutableList()
            updatedList.add(project)
            updatedList.toList()
        }
    }

    override suspend fun removeProject(project: Project) {
        _projectsList.update { currentList ->
            currentList.filterNot { it == project }
        }
    }

    override suspend fun loadFile(uri: Uri?): Boolean {
        if (uri != null) {
            try {
                if (tempFile != null) tempFile!!.delete()
                val mimeType = applicationContext.contentResolver.getType(uri)
                val type = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
                tempFile = withContext(Dispatchers.IO) {
                    File.createTempFile("output", ".$type", applicationContext.cacheDir)
                }
                applicationContext.contentResolver.openInputStream(uri)?.use { inputStream ->
                    val outputStream = FileOutputStream(tempFile)
                    inputStream.copyTo(outputStream)
                    outputStream.close()
                    return true
                }
            } catch (e: IOException) {
                return false
            }
        }
        return false
    }

    private suspend fun saveProjectFile(fileName: String) {
        val outputFile = File(outputDir, fileName)
        tempFile = Compressor.compress(applicationContext, tempFile!!)
        FileUtils.copyFile(tempFile, outputFile)
        tempFile!!.delete()
    }

    override suspend fun takePhoto(photoPath: String): String {
        return try {
            val bitmap = getBitmap(photoPath)
            File(photoPath).delete()
            tempFile = withContext(Dispatchers.IO) {
                File.createTempFile("output", ".jpg", applicationContext.cacheDir)
            }
            tempFile!!.outputStream().use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                out.flush()
            }
            tempFile!!.path
        } catch (e: IOException) {
            ""
        }
    }

    private fun getBitmap(photoPath: String): Bitmap {
        val bitmap = BitmapFactory.decodeFile(photoPath)
        val rotationAngle = when (ExifInterface(photoPath).getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )) {
            ExifInterface.ORIENTATION_ROTATE_90 -> 90
            ExifInterface.ORIENTATION_ROTATE_180 -> 180
            ExifInterface.ORIENTATION_ROTATE_270 -> 270
            else -> 0
        }
        return Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            Matrix().apply { postRotate(rotationAngle.toFloat()) },
            true
        )
    }

    override suspend fun addDrawing(drawing: Drawing) {
        val fileName = drawing.name + ".jpg"
        drawing.drawingFilePath = "$outputDir/$fileName"
        drawing.projectId = currentProject.id
        saveDrawingFile(fileName = fileName)

        _drawingsList.update { currentList ->
            val updatedList = currentList.toMutableList()
            updatedList.add(drawing)
            updatedList.toList()
        }
    }

    private fun saveDrawingFile(fileName: String) {
        val pdfRenderer =
            PdfRenderer(ParcelFileDescriptor.open(tempFile, ParcelFileDescriptor.MODE_READ_ONLY))
        val pdfPage = pdfRenderer.openPage(0)
        val bitmap =
            Bitmap.createBitmap(pdfPage.width, pdfPage.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.WHITE)
        pdfPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        val outputFile = File(outputDir, fileName)
        saveBitmapToFile(bitmap, outputFile)

        pdfPage.close()
        pdfRenderer.close()
        tempFile!!.delete()
    }

    @Throws(IOException::class)
    private fun saveBitmapToFile(bitmap: Bitmap, outputFile: File) {
        val outputStream = FileOutputStream(outputFile)
        outputStream.use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        }
    }

    override suspend fun removeDrawing(drawing: Drawing) {
        _drawingsList.update { currentList ->
            currentList.filterNot { it == drawing }
        }
    }

    override suspend fun addLabel(x: Float, y: Float, name: String, width: Float, height: Float) {
        val fileName = "$name.jpg"
        val widthAndHeight = saveLabelFile(fileName)

        val label = Label(
            name = name,
            labelFilePath = "$outputDir/$fileName",
            drawingId = currentDrawing.id,
            xInApp = x,
            yInApp = y,
            xReal = (widthAndHeight.first / width) * x,
            yReal = (widthAndHeight.second / height) * y
        )

        _labelsList.update { currentList ->
            val updatedList = currentList.toMutableList()
            updatedList.add(label)
            updatedList.toList()
        }
    }

    private suspend fun saveLabelFile(fileName: String): Pair<Int, Int> {
        val outputFile = File(outputDir, fileName)
        tempFile = Compressor.compress(applicationContext, tempFile!!)
        FileUtils.copyFile(tempFile, outputFile)
        tempFile!!.delete()
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(currentDrawing.drawingFilePath, options)
        return Pair(options.outWidth, options.outHeight)
    }

    override suspend fun saveLabel() {
        val outputFile = File(currentLabel.labelFilePath)
        tempFile = Compressor.compress(applicationContext, tempFile!!)
        FileUtils.copyFile(tempFile, outputFile)
        tempFile!!.delete()
    }

    override suspend fun removeLabel() {
        _labelsList.update { currentList ->
            currentList.filterNot { it == currentLabel }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun startRecording(name: String, attachment: AudioAttachment) {
        val outputFilePath = outputDir.toString() + "/" + "$name.3gp"
        val audio = if (attachment == AudioAttachment.ToProject)
            Audio(name = name, audioFilePath = outputFilePath, projectId = currentProject.id)
        else Audio(name = name, audioFilePath = outputFilePath, drawingId = currentDrawing.id)
        addRecording(audio)
        val outputFile = File(outputFilePath)
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(outputFile)
            try {
                prepare()
                start()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun addRecording(audio: Audio) {
        _audioList.update { currentList ->
            val updatedList = currentList.toMutableList()
            updatedList.add(audio)
            updatedList.toList()
        }
    }

    override suspend fun stopRecording() {
        if (recorder != null) {
            recorder?.apply {
                try {
                    stop()
                    release()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            recorder = null
        }
    }
}