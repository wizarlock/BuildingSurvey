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
import com.example.buildingsurvey.data.db.dao.AudioDao
import com.example.buildingsurvey.data.db.dao.DefectDao
import com.example.buildingsurvey.data.db.dao.DefectPointDao
import com.example.buildingsurvey.data.db.dao.DrawingDao
import com.example.buildingsurvey.data.db.dao.LabelDao
import com.example.buildingsurvey.data.db.dao.ProjectDao
import com.example.buildingsurvey.data.db.dao.TextDao
import com.example.buildingsurvey.data.db.dao.TypeOfDefectDao
import com.example.buildingsurvey.data.model.Audio
import com.example.buildingsurvey.data.model.Defect
import com.example.buildingsurvey.data.model.DefectPoint
import com.example.buildingsurvey.data.model.Drawing
import com.example.buildingsurvey.data.model.Label
import com.example.buildingsurvey.data.model.Project
import com.example.buildingsurvey.data.model.Text
import com.example.buildingsurvey.data.model.TypeOfDefect
import com.example.buildingsurvey.ui.screens.AudioAttachment
import com.example.buildingsurvey.ui.screens.workWithDrawing.actions.WorkWithDrawingAction
import com.example.buildingsurvey.utils.toAudio
import com.example.buildingsurvey.utils.toAudioDbEntity
import com.example.buildingsurvey.utils.toDefect
import com.example.buildingsurvey.utils.toDefectDbEntity
import com.example.buildingsurvey.utils.toDefectPoint
import com.example.buildingsurvey.utils.toDefectPointDbEntity
import com.example.buildingsurvey.utils.toDrawing
import com.example.buildingsurvey.utils.toDrawingDbEntity
import com.example.buildingsurvey.utils.toLabel
import com.example.buildingsurvey.utils.toLabelDbEntity
import com.example.buildingsurvey.utils.toProject
import com.example.buildingsurvey.utils.toProjectDbEntity
import com.example.buildingsurvey.utils.toText
import com.example.buildingsurvey.utils.toTextDbEntity
import com.example.buildingsurvey.utils.toTypeOfDefect
import com.example.buildingsurvey.utils.toTypeOfDefectDbEntity
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
import java.io.FileWriter
import java.io.IOException
import java.util.UUID
import javax.inject.Inject

class Repository @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    private val projectDao: ProjectDao,
    private val drawingDao: DrawingDao,
    private val audioDao: AudioDao,
    private val labelDao: LabelDao,
    private val typeOfDefectDao: TypeOfDefectDao,
    private val defectDao: DefectDao,
    private val defectPointDao: DefectPointDao,
    private val textDao: TextDao,

    ) : RepositoryInterface {
    private val _projectsList: MutableStateFlow<List<Project>> = MutableStateFlow(listOf())
    private val _audioList: MutableStateFlow<List<Audio>> = MutableStateFlow(listOf())
    private val _drawingsList: MutableStateFlow<List<Drawing>> = MutableStateFlow(listOf())
    private val _labelsList: MutableStateFlow<List<Label>> = MutableStateFlow(listOf())
    private val _typeOfDefectList: MutableStateFlow<List<TypeOfDefect>> = MutableStateFlow(listOf())
    private val _defectsList: MutableStateFlow<List<Defect>> = MutableStateFlow(listOf())
    private val _defectPointsList: MutableStateFlow<List<DefectPoint>> = MutableStateFlow(listOf())
    private val _textList: MutableStateFlow<List<Text>> = MutableStateFlow(listOf())
    private var recorder: MediaRecorder? = null
    private var widthAndHeight = Pair(0, 0)

    override val projectsList = _projectsList.asStateFlow()
    override val drawingsList = _drawingsList.asStateFlow()
    override val audioList = _audioList.asStateFlow()
    override val labelsList = _labelsList.asStateFlow()
    override val defectsList = _defectsList.asStateFlow()
    override val defectPointsList = _defectPointsList.asStateFlow()
    override val typeOfDefectList = _typeOfDefectList.asStateFlow()
    override val textList = _textList.asStateFlow()
    override var widthAndHeightApp = Pair(0f, 0f)

    override var currentProject = Project()
    override var currentDrawing = Drawing()

    override var currentLabel = Label()

    private var tempFile: File? = null
    private val outputDir = applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)

    override suspend fun loadDataFromDB() {
        withContext(Dispatchers.IO) {
            _projectsList.value = projectDao.getAllProjects().map { it.toProject() }
            _drawingsList.value = drawingDao.getAllDrawings().map { it.toDrawing() }
            _audioList.value = audioDao.getAllAudio().map { it.toAudio() }
            _labelsList.value = labelDao.getAllLabels().map { it.toLabel() }
            _typeOfDefectList.value = typeOfDefectDao.getAllTypes().map { it.toTypeOfDefect() }
            _defectsList.value = defectDao.getAllDefects().map { it.toDefect() }
            _defectPointsList.value = defectPointDao.getAllDefectPoints().map { it.toDefectPoint() }
            _textList.value = textDao.getAllTexts().map { it.toText() }
        }
    }

    override suspend fun updateCurrentDrawing(drawing: Drawing) {
        currentDrawing = drawing
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(drawing.drawingFilePath, options)
        widthAndHeight = Pair(options.outWidth, options.outHeight)
    }

    override suspend fun addProject(project: Project, isFileExists: Boolean) =
        withContext(Dispatchers.IO) {
            if (isFileExists) {
                val name = tempFile!!.name
                val lastDotIndex = name.lastIndexOf(".")
                val fileName = UUID.randomUUID().toString() + name.substring(lastDotIndex)
                project.projectFilePath = "$outputDir/$fileName"
                saveProjectFile(fileName = fileName)
            }
            projectDao.insert(project.toProjectDbEntity())
            _projectsList.update { currentList ->
                val updatedList = currentList.toMutableList()
                updatedList.add(project)
                updatedList.toList()
            }
        }


    override suspend fun removeProject(project: Project) =
        withContext(Dispatchers.IO) {
            projectDao.delete(project.toProjectDbEntity())
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

    override suspend fun addDrawing(drawing: Drawing) =
        withContext(Dispatchers.IO) {
            val fileName = drawing.name + ".jpg"
            drawing.drawingFilePath = "$outputDir/$fileName"
            drawing.projectId = currentProject.id
            saveDrawingFile(fileName = fileName)

            drawingDao.insert(drawing.toDrawingDbEntity())
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

    override suspend fun removeDrawing(drawing: Drawing) =
        withContext(Dispatchers.IO) {
            drawingDao.delete(drawing.toDrawingDbEntity())
            _drawingsList.update { currentList ->
                currentList.filterNot { it == drawing }
            }
        }

    override suspend fun addLabel(x: Float, y: Float, name: String) =
        withContext(Dispatchers.IO) {
            val fileName = "$name.jpg"
            saveLabelFile(fileName)

            val label = Label(
                name = name,
                labelFilePath = "$outputDir/$fileName",
                drawingId = currentDrawing.id,
                xInApp = x,
                yInApp = y,
                xReal = (widthAndHeight.first / widthAndHeightApp.first) * x,
                yReal = (widthAndHeight.second / widthAndHeightApp.second) * y
            )
            labelDao.insert(label.toLabelDbEntity())
            _labelsList.update { currentList ->
                val updatedList = currentList.toMutableList()
                updatedList.add(label)
                updatedList.toList()
            }
        }

    private suspend fun saveLabelFile(fileName: String) {
        val outputFile = File(outputDir, fileName)
        tempFile = Compressor.compress(applicationContext, tempFile!!)
        FileUtils.copyFile(tempFile, outputFile)
        tempFile!!.delete()
    }

    override suspend fun addDefect(defect: Defect, points: List<DefectPoint>) =
        withContext(Dispatchers.IO) {
            _defectsList.update { currentList ->
                val updatedList = currentList.toMutableList()
                updatedList.add(defect)
                updatedList.toList()
            }
            defectDao.insert(defect.toDefectDbEntity())
            points.forEach { point ->
                point.xReal = (widthAndHeight.first / widthAndHeightApp.first) * point.xInApp
                point.yReal = (widthAndHeight.second / widthAndHeightApp.second) * point.yInApp
            }
            points.forEach { point ->
                defectPointDao.insert(point.toDefectPointDbEntity())
            }

            _defectPointsList.update { currentList ->
                val updatedList = currentList.toMutableList()
                points.forEach { point ->
                    updatedList.add(point)
                }
                updatedList.toList()
            }
        }

    override suspend fun removeDefect(defect: Defect) =
        withContext(Dispatchers.IO) {
            defectDao.delete(defect.toDefectDbEntity())
            val pointsToDelete = _defectPointsList.value.filter { it.defectId == defect.id }
            pointsToDelete.forEach { point ->
                defectPointDao.delete(point.toDefectPointDbEntity())
                _defectPointsList.update { currentList ->
                    currentList.filterNot { it == point }
                }
            }
            _defectsList.update { currentList ->
                currentList.filterNot { it == defect }
            }

        }

    override suspend fun saveLabel() {
        val outputFile = File(currentLabel.labelFilePath)
        tempFile = Compressor.compress(applicationContext, tempFile!!)
        FileUtils.copyFile(tempFile, outputFile)
        tempFile!!.delete()
    }

    override suspend fun removeLabel() =
        withContext(Dispatchers.IO) {
            labelDao.delete(currentLabel.toLabelDbEntity())
            _labelsList.update { currentList ->
                currentList.filterNot { it == currentLabel }
            }
        }

    override suspend fun addTypeOfDefect(typeOfDefect: TypeOfDefect) =
        withContext(Dispatchers.IO) {
            typeOfDefectDao.insert(typeOfDefect.toTypeOfDefectDbEntity())
            _typeOfDefectList.update { currentList ->
                val updatedList = currentList.toMutableList()
                updatedList.add(typeOfDefect)
                updatedList.toList()
            }
        }


    override suspend fun addText(text: Text) =
        withContext(Dispatchers.IO) {
            text.xReal = (widthAndHeight.first / widthAndHeightApp.first) * text.xInApp
            text.yReal = (widthAndHeight.second / widthAndHeightApp.second) * text.yInApp
            textDao.insert(text.toTextDbEntity())
            _textList.update { currentList ->
                val updatedList = currentList.toMutableList()
                updatedList.add(text)
                updatedList.toList()
            }
        }

    override suspend fun removeText(text: Text) =
        withContext(Dispatchers.IO) {
            textDao.delete(text.toTextDbEntity())
            _textList.update { currentList ->
                currentList.filterNot { it == text }
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

    private suspend fun addRecording(audio: Audio) =
        withContext(Dispatchers.IO) {
            audioDao.insert(audio.toAudioDbEntity())
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

    override suspend fun export() {
        val directoryPath = File(
            outputDir,
            "Export"
        )
        if (!directoryPath.exists()) {
            directoryPath.mkdirs()
        }
        val fileName = currentDrawing.name + ".txt"
        val file = File(directoryPath, fileName)
        file.createNewFile()
        try {
            val writer = FileWriter(file)
            writer.append(projectsList.value.find { it.id == currentDrawing.projectId }!!.name)
            writer.appendLine()
            writer.append(currentDrawing.name)
            writer.appendLine()
            _audioList.value.filter { it.drawingId == currentDrawing.id }.forEach { audio ->
                writer.append("Аудио: " + audio.name + ".3gp")
                writer.appendLine()
            }
            _labelsList.value.filter { it.drawingId == currentDrawing.id }.forEach { label ->
                writer.append("Фото: (" + label.xReal + "/" + label.yReal + "), " + label.name)
                writer.appendLine()
            }
            _defectsList.value.filter { it.drawingId == currentDrawing.id }.forEach { defect ->
                val list = _defectPointsList.value.filter { it.defectId == defect.id }
                val nameColor = if (defect.hexCode == "#FF000000") "0 "
                    else _typeOfDefectList.value.find { it.hexCode == defect.hexCode }!!.name
                when (list.size) {
                    1 -> {
                        writer.append("Точечный дефект: (" + list.first().xReal + "/" + list.first().yReal + "), " + nameColor + "_" + defect.hexCode)
                        writer.appendLine()
                    }

                    2 -> {
                        writer.append("Линия: (" + list.first().xReal + "/" + list.first().yReal + "; " + list.last().xReal + "/" + list.last().yReal + "), " + nameColor + "_" + defect.hexCode)
                        writer.appendLine()
                    }

                    4 -> {
                        if (defect.isClosed == 1 && (list[1].xReal == list.first().xReal && list[1].yReal == list[2].yReal) && (list.last().xReal == list[2].xReal && list.last().yReal == list.first().yReal))
                            writer.append("Прямоугольник: (")
                        else if (defect.isClosed == 1) writer.append("Замкнутая ломанная линия: (")
                        else writer.append("Ломанная линия: (")
                        for (i in list.indices) {
                            writer.append(list[i].xReal.toString() + "/" + list[i].yReal)
                            if (i != list.size - 1)
                                writer.append("; ")
                        }
                        writer.append("), " + nameColor + "_" + defect.hexCode)
                        writer.appendLine()
                    }

                    else -> {
                        if (defect.isClosed == 1) writer.append("Замкнутая ломанная линия: (")
                        else writer.append("Ломанная линия: (")
                        for (i in list.indices) {
                            writer.append(list[i].xReal.toString() + "/" + list[i].yReal)
                            if (i != list.size - 1)
                                writer.append("; ")
                        }
                        writer.append("), " + nameColor + "_" + defect.hexCode)
                        writer.appendLine()
                    }
                }
            }
            _textList.value.filter { it.drawingId == currentDrawing.id }.forEach { text ->
                writer.append("Текст: (${text.xReal}/${text.yReal}), \"${text.text}\"")
                writer.appendLine()
            }
            writer.flush()
            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}