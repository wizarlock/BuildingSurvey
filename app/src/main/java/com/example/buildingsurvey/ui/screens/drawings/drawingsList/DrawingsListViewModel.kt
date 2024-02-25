package com.example.buildingsurvey.ui.screens.drawings.drawingsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buildingsurvey.data.RepositoryInterface
import com.example.buildingsurvey.data.datastore.DataStoreManager
import com.example.buildingsurvey.data.model.Drawing
import com.example.buildingsurvey.data.model.Project
import com.example.buildingsurvey.ui.screens.AudioAttachment
import com.example.buildingsurvey.ui.screens.drawings.drawingsList.actions.DrawingsListAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrawingsListViewModel @Inject constructor(
    private val repository: RepositoryInterface,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(DrawingsListUiState())
    val uiState = _uiState.asStateFlow()
    private var project = Project()

    init {
        viewModelScope.launch {
            project = repository.currentProject
            _uiState.update {
                uiState.value.copy(
                    projectName = project.name,
                    drawings = repository.drawingsList.map { drawings ->
                        drawings.filter { it.projectId == project.id }
                    }.stateIn(viewModelScope)
                )
            }
            viewModelScope.launch {
                dataStoreManager.userPreferences.collectLatest { userPref ->
                    _uiState.update {
                        uiState.value.copy(
                            audioNum = userPref.audioNum
                        )
                    }
                }
            }
        }
    }

    fun onUiAction(action: DrawingsListAction) {
        when (action) {
            is DrawingsListAction.DeleteDrawing -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.removeDrawing(action.drawing)
                }
            }

            is DrawingsListAction.UpdateDrawing -> viewModelScope.launch(Dispatchers.IO) {
                repository.updateCurrentDrawing(drawing = action.drawing)
            }

            is DrawingsListAction.UpdateAudioNum -> {
                _uiState.update {
                    uiState.value.copy(
                        audioNum = action.num
                    )
                }
                viewModelScope.launch {
                    dataStoreManager.updateAudioNum(action.num)
                }
            }

            is DrawingsListAction.StartRecord -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.startRecording(action.name, AudioAttachment.ToProject)
                }
            }

            DrawingsListAction.StopRecord -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.stopRecording()
                }
            }
        }
    }
}

data class DrawingsListUiState(
    private val _drawings: MutableStateFlow<List<Drawing>> = MutableStateFlow(listOf()),
    val projectName: String = "",
    val drawings: StateFlow<List<Drawing>> = _drawings.asStateFlow(),
    val audioNum: Int = 0
)