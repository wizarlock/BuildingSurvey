package com.example.buildingsurvey.ui.screens.projects.addProject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buildingsurvey.data.RepositoryInterface
import com.example.buildingsurvey.data.model.Project
import com.example.buildingsurvey.ui.screens.isNotRepeatName
import com.example.buildingsurvey.ui.screens.isValidName
import com.example.buildingsurvey.ui.screens.projects.addProject.actions.AddProjectAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProjectViewModel @Inject constructor(
    private val repository: RepositoryInterface
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddProjectUiState())
    val uiState = _uiState.asStateFlow()

    fun onUiAction(action: AddProjectAction) {
        when (action) {
            is AddProjectAction.UpdateProjectName -> _uiState.update {
                uiState.value.copy(projectName = action.name)
            }

            is AddProjectAction.UpdateSelectedFile -> {
                viewModelScope.launch {
                    val isFileExists = repository.loadFile(uri = action.uri)
                    if (isFileExists) {
                        _uiState.update {
                            uiState.value.copy(isFileExists = true)
                        }
                    } else _uiState.update {
                        uiState.value.copy(isFileExists = false)
                    }
                }
            }

            is AddProjectAction.UpdatePhotoFile -> {
                viewModelScope.launch {
                    val isFileExists = repository.takePhoto(photoPath = action.path).isNotEmpty()
                    if (isFileExists) {
                        _uiState.update {
                            uiState.value.copy(isFileExists = true)
                        }
                    } else _uiState.update {
                        uiState.value.copy(isFileExists = false)
                    }
                }
            }

            AddProjectAction.SaveProject -> saveProject()
        }
    }

    fun areFieldsValid(): Boolean {
        val isValidProjectName = isValidName(
            name = uiState.value.projectName,
        )

        val isNotRepeatProjectName = isNotRepeatName(
            name = uiState.value.projectName,
            list = repository.projectsList.value.map { it.name }
        )

        _uiState.update {
            uiState.value.copy(
                isValidProjectName = isValidProjectName,
                isNotRepeatProjectName = isNotRepeatProjectName
            )
        }

        return isValidProjectName && isNotRepeatProjectName
    }

    private fun saveProject() {
        val project = Project(name = uiState.value.projectName)
        viewModelScope.launch(Dispatchers.IO) {
            repository.addProject(
                project = project,
                isFileExists = uiState.value.isFileExists
            )
        }
    }

    data class AddProjectUiState(
        val projectName: String = "",
        val isValidProjectName: Boolean = true,
        val isNotRepeatProjectName: Boolean = true,
        val isFileExists: Boolean = false,
    )
}