package com.example.buildingsurvey.ui.screens.updateLabel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buildingsurvey.data.RepositoryInterface
import com.example.buildingsurvey.data.model.Label
import com.example.buildingsurvey.ui.screens.updateLabel.actions.UpdateLabelAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateLabelViewModel @Inject constructor(
    private val repository: RepositoryInterface
) : ViewModel() {
    private val _uiState = MutableStateFlow(UpdateLabelUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                uiState.value.copy(
                    label = repository.currentLabel,
                    newPhotoPath = repository.currentLabel.labelFilePath
                )
            }
        }
    }

    fun onUiAction(action: UpdateLabelAction) {
        when (action) {
            is UpdateLabelAction.SaveLabel -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.saveLabel()
                }
            }

            is UpdateLabelAction.TakePhoto -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val photoPath = repository.takePhoto(action.photoPath)
                    _uiState.update {
                        uiState.value.copy(
                            newPhotoPath = photoPath
                        )
                    }
                }
            }

            UpdateLabelAction.DeleteLabel -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.removeLabel()
                }
            }
        }
    }


    data class UpdateLabelUiState(
        var label: Label = Label(),
        var newPhotoPath: String = ""
        )
}

