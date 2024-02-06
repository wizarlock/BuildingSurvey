package com.example.buildingsurvey.ui.screens.workWithDrawing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buildingsurvey.data.RepositoryInterface
import com.example.buildingsurvey.data.datastore.DataStoreManager
import com.example.buildingsurvey.data.model.Drawing
import com.example.buildingsurvey.data.model.Project
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
    private var project = Project()
    private var drawing = Drawing()

    init {
        viewModelScope.launch {
            project = repository.currentProject
            drawing = repository.currentDrawing
            _uiState.update {
                uiState.value.copy(
                    drawings = repository.drawingsList.map { drawings ->
                        drawings.filter { it.projectId == project.id }
                    }.stateIn(viewModelScope),
                    drawingFilePath = drawing.drawingFilePath
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

            WorkWithDrawingAction.StopRecord -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.stopRecording()
                }
            }
        }
    }
}

data class WorkWithDrawingUiState(
    private val _drawings: MutableStateFlow<List<Drawing>> = MutableStateFlow(listOf()),
    val drawings: StateFlow<List<Drawing>> = _drawings.asStateFlow(),
    val audioNum: Int = 0,
    val drawingFilePath: String = ""
)