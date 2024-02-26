package com.example.buildingsurvey.data

import android.net.Uri
import com.example.buildingsurvey.data.model.Audio
import com.example.buildingsurvey.data.model.Defect
import com.example.buildingsurvey.data.model.DefectPoint
import com.example.buildingsurvey.data.model.Drawing
import com.example.buildingsurvey.data.model.Label
import com.example.buildingsurvey.data.model.Project
import com.example.buildingsurvey.data.model.Text
import com.example.buildingsurvey.data.model.TypeOfDefect
import com.example.buildingsurvey.ui.screens.AudioAttachment
import kotlinx.coroutines.flow.StateFlow

interface RepositoryInterface {
    val projectsList: StateFlow<List<Project>>

    val drawingsList: StateFlow<List<Drawing>>

    val audioList: StateFlow<List<Audio>>

    val labelsList: StateFlow<List<Label>>

    val typeOfDefectList: StateFlow<List<TypeOfDefect>>

    val defectsList: StateFlow<List<Defect>>

    val defectPointsList: StateFlow<List<DefectPoint>>

    val textList: StateFlow<List<Text>>


    var currentProject: Project

    var currentDrawing: Drawing

    var currentLabel: Label

    var widthAndHeightApp: Pair<Float, Float>

    suspend fun updateCurrentDrawing(drawing: Drawing)

    suspend fun stopRecording()

    suspend fun startRecording(name: String, attachment: AudioAttachment)

    suspend fun addProject(project: Project, isFileExists: Boolean)

    suspend fun removeProject(project: Project)

    suspend fun loadFile(uri: Uri?): Boolean

    suspend fun takePhoto(photoPath: String): String

    suspend fun addDrawing(drawing: Drawing)

    suspend fun removeDefect(defect: Defect)

    suspend fun removeDrawing(drawing: Drawing)

    suspend fun addText(text: Text)

    suspend fun removeText(text: Text)

    suspend fun addLabel(x: Float, y: Float, name: String)

    suspend fun saveLabel()

    suspend fun removeLabel()

    suspend fun addTypeOfDefect(typeOfDefect: TypeOfDefect)

    suspend fun loadDataFromDB()

    suspend fun addDefect(defect: Defect, points: List<DefectPoint>)

    suspend fun export()
}