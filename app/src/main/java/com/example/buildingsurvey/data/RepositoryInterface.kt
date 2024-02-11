package com.example.buildingsurvey.data

import android.net.Uri
import com.example.buildingsurvey.data.model.Audio
import com.example.buildingsurvey.data.model.Drawing
import com.example.buildingsurvey.data.model.Label
import com.example.buildingsurvey.data.model.Project
import com.example.buildingsurvey.ui.screens.AudioAttachment
import kotlinx.coroutines.flow.StateFlow

interface RepositoryInterface {
    val projectsList: StateFlow<List<Project>>

    val drawingsList: StateFlow<List<Drawing>>

    val audioList: StateFlow<List<Audio>>

    val labelsList: StateFlow<List<Label>>

    var currentProject: Project

    var currentDrawing: Drawing

    var currentLabel: Label

    suspend fun stopRecording()

    suspend fun startRecording(name: String, attachment: AudioAttachment)

    suspend fun addProject(project: Project, isFileExists: Boolean)

    suspend fun removeProject(project: Project)

    suspend fun loadFile(uri: Uri?): Boolean

    suspend fun takePhoto(photoPath: String): String

    suspend fun addDrawing(drawing: Drawing)

    suspend fun removeDrawing(drawing: Drawing)

    suspend fun addLabel(x: Float, y: Float, name: String, width: Float, height: Float)

    suspend fun saveLabel()

    suspend fun removeLabel()
}