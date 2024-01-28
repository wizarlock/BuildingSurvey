package com.example.buildingsurvey.ui.screens.projects.addProject.actions

import android.net.Uri

sealed class AddProjectAction {
    data class UpdateProjectName(val name: String) : AddProjectAction()
    data class UpdateSelectedFile(val uri: Uri?) : AddProjectAction()
    data class UpdatePhotoFile(val path: String) : AddProjectAction()
    object SaveProject : AddProjectAction()
}