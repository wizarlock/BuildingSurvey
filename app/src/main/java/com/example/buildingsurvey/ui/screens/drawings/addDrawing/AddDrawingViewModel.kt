package com.example.buildingsurvey.ui.screens.drawings.addDrawing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buildingsurvey.data.RepositoryInterface
import com.example.buildingsurvey.data.model.Drawing
import com.example.buildingsurvey.ui.screens.drawings.addDrawing.actions.AddDrawingAction
import com.example.buildingsurvey.ui.screens.isNotRepeatName
import com.example.buildingsurvey.ui.screens.isValidName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddDrawingViewModel @Inject constructor(
    private val repository: RepositoryInterface
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddDrawingUiState())
    val uiState = _uiState.asStateFlow()

    fun onUiAction(action: AddDrawingAction) {
        when (action) {
            is AddDrawingAction.UpdateDrawingName -> _uiState.update {
                uiState.value.copy(drawingName = action.name)
            }

            is AddDrawingAction.UpdateSelectedFile -> {
                viewModelScope.launch {
                    val isFileExists = repository.loadFile(uri = action.uri)
                    if (isFileExists) {
                        _uiState.update {
                            uiState.value.copy(isDrawingFileAttached = true)
                        }
                    } else _uiState.update {
                        uiState.value.copy(isDrawingFileAttached = false)
                    }
                }
            }
            AddDrawingAction.SaveDrawing -> saveDrawing()
        }
    }

    fun areFieldsValid(): Boolean {
        val isValidDrawingName = isValidName(
            name = uiState.value.drawingName,
        )

        val isNotRepeatDrawingName = isNotRepeatName(
            name = uiState.value.drawingName,
            list = repository.drawingsList.value.map { it.name }
        )

        val isDrawingFileValid = uiState.value.isDrawingFileAttached

        _uiState.update {
            uiState.value.copy(
                isValidDrawingName = isValidDrawingName,
                isNotRepeatDrawingName = isNotRepeatDrawingName,
                isDrawingFileValid = isDrawingFileValid
            )
        }

        return isValidDrawingName && isNotRepeatDrawingName && isDrawingFileValid
    }

    private fun saveDrawing() {
        val drawing = Drawing(name = uiState.value.drawingName)
        viewModelScope.launch(Dispatchers.IO) {
            repository.addDrawing(
                drawing = drawing,
            )
        }
    }
}

data class AddDrawingUiState(
    val drawingName: String = "",
    val isValidDrawingName: Boolean = true,
    val isNotRepeatDrawingName: Boolean = true,
    val isDrawingFileAttached: Boolean = false,
    val isDrawingFileValid: Boolean = true
)