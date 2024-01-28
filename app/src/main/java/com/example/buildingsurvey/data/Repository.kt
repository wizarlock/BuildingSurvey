package com.example.buildingsurvey.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import android.webkit.MimeTypeMap
import com.example.buildingsurvey.data.model.Project
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
    private var tempFile: File? = null
    private val outputDir = applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
    override val projectsList = _projectsList.asStateFlow()
    override var currentProject = Project()

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

    override suspend fun takePhoto(photoPath: String): Boolean {
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
            true
        } catch (e: IOException) {
            false
        }
    }

    private fun getBitmap(photoPath: String): Bitmap {
        val bitmap = BitmapFactory.decodeFile(photoPath)
        val rotationAngle = when (ExifInterface(photoPath).getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
            ExifInterface.ORIENTATION_ROTATE_90 -> 90
            ExifInterface.ORIENTATION_ROTATE_180 -> 180
            ExifInterface.ORIENTATION_ROTATE_270 -> 270
            else -> 0
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, Matrix().apply { postRotate(rotationAngle.toFloat()) }, true)
    }
}