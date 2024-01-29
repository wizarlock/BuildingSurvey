package com.example.buildingsurvey.ui.screens.drawings.addDrawing.actions

import android.net.Uri

sealed class AddDrawingAction {
    data class UpdateDrawingName(val name: String) : AddDrawingAction()

    data class UpdateSelectedFile(val uri: Uri?) : AddDrawingAction()

    object SaveDrawing : AddDrawingAction()
}