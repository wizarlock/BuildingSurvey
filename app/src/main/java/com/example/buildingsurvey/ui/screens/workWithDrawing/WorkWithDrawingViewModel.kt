package com.example.buildingsurvey.ui.screens.workWithDrawing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buildingsurvey.data.RepositoryInterface
import com.example.buildingsurvey.data.datastore.DataStoreManager
import com.example.buildingsurvey.data.model.Drawing
import com.example.buildingsurvey.ui.screens.AudioAttachment
import com.example.buildingsurvey.ui.screens.workWithDrawing.actions.WorkWithDrawingAction
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
class WorkWithDrawingViewModel @Inject constructor(
    private val repository: RepositoryInterface,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(WorkWithDrawingUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                uiState.value.copy(
                    drawings = repository.drawingsList.map { drawings ->
                        drawings.filter { it.projectId == repository.currentProject.id }
                    }.stateIn(viewModelScope)
                )
            }
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
        initDrawing()
    }

    private fun initDrawing() {
        viewModelScope.launch {
            _uiState.update {
                uiState.value.copy(
                    currentDrawing = repository.currentDrawing,
                )
            }
        }
    }

    fun onUiAction(action: WorkWithDrawingAction) {
        when (action) {
            is WorkWithDrawingAction.UpdateAudioNum -> {
                _uiState.update {
                    uiState.value.copy(
                        audioNum = action.num
                    )
                }
                viewModelScope.launch {
                    dataStoreManager.updateAudioNum(action.num)
                }
            }

            is WorkWithDrawingAction.StartRecord -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.startRecording(action.name, AudioAttachment.ToProject)
                }
            }

            is WorkWithDrawingAction.UpdateDrawing -> {
                repository.currentDrawing = action.drawing
                initDrawing()
            }

            is WorkWithDrawingAction.UpdateScaleAndOffset -> {
                _uiState.update {
                    uiState.value.copy(
                        offsetX = uiState.value.offsetX + action.pan.x,
                        offsetY = uiState.value.offsetY + action.pan.y,
                        scale = uiState.value.scale * action.zoom
                    )
                }
            }

            WorkWithDrawingAction.StopRecord -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.stopRecording()
                }
            }

            WorkWithDrawingAction.UpdatePhotoMode -> {
                _uiState.update {
                    uiState.value.copy(
                        photoMode = !uiState.value.photoMode
                    )
                }
            }

            WorkWithDrawingAction.ReturnBackScaleAndOffset -> {
                _uiState.update {
                    uiState.value.copy(
                        offsetX = 0f,
                        offsetY = 0f,
                        scale = 1f
                    )
                }
            }
        }
    }
}

data class WorkWithDrawingUiState(
    private val _drawings: MutableStateFlow<List<Drawing>> = MutableStateFlow(listOf()),
    val drawings: StateFlow<List<Drawing>> = _drawings.asStateFlow(),
    val audioNum: Int = 0,
    val photoMode: Boolean = false,
    val scale: Float = 1f,
    val offsetX: Float = 0f,
    val offsetY: Float = 0f,
    var currentDrawing: Drawing = Drawing()
)