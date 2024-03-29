package com.example.buildingsurvey.ui.screens.workWithDrawing

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buildingsurvey.data.RepositoryInterface
import com.example.buildingsurvey.data.datastore.DataStoreManager
import com.example.buildingsurvey.data.model.Defect
import com.example.buildingsurvey.data.model.DefectPoint
import com.example.buildingsurvey.data.model.Drawing
import com.example.buildingsurvey.data.model.Label
import com.example.buildingsurvey.data.model.Text
import com.example.buildingsurvey.data.model.TypeOfDefect
import com.example.buildingsurvey.ui.screens.AudioAttachment
import com.example.buildingsurvey.ui.screens.isValidName
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
        val defaultColour = TypeOfDefect(
            name = "0",
            hexCode = "#FF000000"
        )
        viewModelScope.launch {
            _uiState.update {
                uiState.value.copy(
                    drawings = repository.drawingsList.map { drawings ->
                        drawings.filter { it.projectId == repository.currentProject.id }
                    }.stateIn(viewModelScope),
                    typeOfDefect = repository.typeOfDefectList.map { typeOfDefect ->
                        listOf(defaultColour) + typeOfDefect.filter { it.projectId == repository.currentProject.id }
                    }.stateIn(viewModelScope),
                    selectedType = defaultColour
                )
            }
        }

        viewModelScope.launch {
            dataStoreManager.userPreferences.collectLatest { userPref ->
                _uiState.update {
                    uiState.value.copy(
                        photoNum = userPref.photoNum,
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
                    labels = repository.labelsList.map { labels ->
                        labels.filter { it.drawingId == repository.currentDrawing.id }
                    }.stateIn(viewModelScope),
                    defectsList = repository.defectsList.map { defects ->
                        defects.filter { it.drawingId == repository.currentDrawing.id }
                    }.stateIn(viewModelScope),
                    defectPointsList = repository.defectPointsList.map { defectPoints ->
                        defectPoints.filter { it.drawingId == repository.currentDrawing.id }
                    }.stateIn(viewModelScope),
                    texts = repository.textList.map { texts ->
                        texts.filter { it.drawingId == repository.currentDrawing.id }
                    }.stateIn(viewModelScope),
                    changesList = ChangesList(),
                    linesForBrokenLine = mutableListOf(),
                    text = "",
                    coordinatesOfText = Pair(-1f, -1f),
                    isBackEnable = false,
                    isForwardEnable = false,
                    photoMode = false,
                    audioMode = false,
                    textSelected = false,
                    frameSelected = false,
                    drawingBrokenLine = false,
                    brokenLineSelected = false,
                    lineSegmentSelected = false,
                    pointDefectSelected = false,
                    swipeMode = true,
                    scale = 1f,
                    offsetX = 0f,
                    offsetY = 0f
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
                    _uiState.update {
                        uiState.value.copy(
                            audioMode = true
                        )
                    }
                    repository.startRecording(action.name, AudioAttachment.ToDrawing)
                }
            }

            is WorkWithDrawingAction.UpdateDrawing -> {
                viewModelScope.launch(Dispatchers.IO) {
                    if (uiState.value.audioMode) onUiAction(WorkWithDrawingAction.StopRecord)
                    repository.updateCurrentDrawing(drawing = action.drawing)
                    initDrawing()
                }
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

            is WorkWithDrawingAction.UpdateLabel -> repository.currentLabel = action.label

            WorkWithDrawingAction.StopRecord -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _uiState.update {
                        uiState.value.copy(
                            audioMode = false
                        )
                    }
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

            is WorkWithDrawingAction.UpdateDrawingBrokenLine -> {
                _uiState.update {
                    uiState.value.copy(
                        drawingBrokenLine = action.isDrawing
                    )
                }
            }

            WorkWithDrawingAction.UpdateSwipeMode -> {
                _uiState.update {
                    uiState.value.copy(
                        swipeMode = !uiState.value.swipeMode
                    )
                }
            }

            WorkWithDrawingAction.UpdateTextSelected -> {
                _uiState.update {
                    uiState.value.copy(
                        frameSelected = false,
                        brokenLineSelected = false,
                        lineSegmentSelected = false,
                        pointDefectSelected = false,
                        textSelected = !uiState.value.textSelected,
                    )
                }
            }

            WorkWithDrawingAction.UpdateFrameSelected -> {
                _uiState.update {
                    uiState.value.copy(
                        brokenLineSelected = false,
                        lineSegmentSelected = false,
                        pointDefectSelected = false,
                        textSelected = false,
                        frameSelected = !uiState.value.frameSelected,
                    )
                }
            }

            WorkWithDrawingAction.UpdateBrokenLineSelected -> {
                _uiState.update {
                    uiState.value.copy(
                        lineSegmentSelected = false,
                        pointDefectSelected = false,
                        textSelected = false,
                        frameSelected = false,
                        brokenLineSelected = !uiState.value.brokenLineSelected,
                    )
                }
            }

            WorkWithDrawingAction.UpdateLineSegmentSelected -> {
                _uiState.update {
                    uiState.value.copy(
                        pointDefectSelected = false,
                        textSelected = false,
                        frameSelected = false,
                        brokenLineSelected = false,
                        lineSegmentSelected = !uiState.value.lineSegmentSelected,
                    )
                }
            }

            WorkWithDrawingAction.UpdatePointDefectSelected -> {
                _uiState.update {
                    uiState.value.copy(
                        textSelected = false,
                        frameSelected = false,
                        brokenLineSelected = false,
                        lineSegmentSelected = false,
                        pointDefectSelected = !uiState.value.pointDefectSelected
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

            is WorkWithDrawingAction.CreateLabel -> {
                viewModelScope.launch {
                    val isFileExists = repository.takePhoto(photoPath = action.path).isNotEmpty()
                    if (isFileExists) {
                        val newPhotoNum = uiState.value.photoNum + 1
                        _uiState.update {
                            uiState.value.copy(
                                photoNum = newPhotoNum
                            )
                        }
                        dataStoreManager.updatePhotoNum(newPhotoNum)
                        repository.addLabel(
                            x = action.x,
                            y = action.y,
                            name = newPhotoNum.toString()
                        )
                    }
                }
            }

            is WorkWithDrawingAction.UpdateSelectedTypeOfDefect -> {
                _uiState.update {
                    uiState.value.copy(
                        selectedType = action.typeOfDefect
                    )
                }
            }

            is WorkWithDrawingAction.Back -> {
                viewModelScope.launch(Dispatchers.IO) {
                    when (action.value) {
                        is Defect -> repository.removeDefect(defect = action.value)
                        is Text -> repository.removeText(text = action.value)
                    }
                    _uiState.update {
                        uiState.value.copy(
                            isForwardEnable = uiState.value.changesList.forwardIsAvailable(),
                            isBackEnable = uiState.value.changesList.backIsAvailable()
                        )
                    }
                }
            }

            is WorkWithDrawingAction.Forward -> {
                viewModelScope.launch(Dispatchers.IO) {
                    when (action.value) {
                        is Defect -> repository.addDefect(defect = action.value, points = listOf())
                        is Text -> repository.addText(text = action.value)
                    }

                    _uiState.update {
                        uiState.value.copy(
                            isForwardEnable = uiState.value.changesList.forwardIsAvailable(),
                            isBackEnable = uiState.value.changesList.backIsAvailable()
                        )
                    }
                }
            }

            is WorkWithDrawingAction.AddDefect -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val defect = Defect(
                        drawingId = uiState.value.currentDrawing.id,
                        hexCode = uiState.value.selectedType.hexCode,
                        isClosed = action.isClosed
                    )
                    uiState.value.changesList.add(value = defect)
                    val listOfPoints = mutableListOf<DefectPoint>()
                    for (index in action.points.indices) {
                        listOfPoints.add(
                            DefectPoint(
                                defectId = defect.id,
                                drawingId = uiState.value.currentDrawing.id,
                                position = index,
                                xInApp = action.points[index].x,
                                yInApp = action.points[index].y
                            )
                        )
                    }
                    repository.addDefect(defect = defect, points = listOfPoints)
                    _uiState.update {
                        uiState.value.copy(
                            isForwardEnable = uiState.value.changesList.forwardIsAvailable(),
                            isBackEnable = uiState.value.changesList.backIsAvailable()
                        )
                    }
                }
            }

            WorkWithDrawingAction.Export -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.export()
                }
            }

            is WorkWithDrawingAction.UpdateLinesForBrokenLine -> {
                _uiState.update {
                    uiState.value.copy(
                        linesForBrokenLine = action.linesForBrokenLine
                    )
                }
            }

            is WorkWithDrawingAction.LoadAppWidthAndHeight -> {
                repository.widthAndHeightApp = Pair(action.width, action.height)
            }

            is WorkWithDrawingAction.UpdateText -> _uiState.update {
                uiState.value.copy(text = action.text)
            }

            is WorkWithDrawingAction.UpdateCoordinatesOfText -> _uiState.update {
                uiState.value.copy(coordinatesOfText = action.coordinates)
            }

            WorkWithDrawingAction.AddText -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val text = Text(
                        text = uiState.value.text,
                        drawingId = uiState.value.currentDrawing.id,
                        xInApp = uiState.value.coordinatesOfText.first,
                        yInApp = uiState.value.coordinatesOfText.second
                    )
                    repository.addText(text = text)
                    uiState.value.changesList.add(value = text)
                    _uiState.update {
                        uiState.value.copy(
                            isForwardEnable = uiState.value.changesList.forwardIsAvailable(),
                            isBackEnable = uiState.value.changesList.backIsAvailable()
                        )
                    }
                    onUiAction(WorkWithDrawingAction.UpdateCoordinatesOfText(Pair(-1f, -1f)))
                    onUiAction(WorkWithDrawingAction.UpdateText(""))
                }
            }
        }
    }
    fun areFieldsValid(): Boolean {
        val isValidText = isValidName(
            name = uiState.value.text,
        )

        _uiState.update {
            uiState.value.copy(
                isValidText = isValidText,
            )
        }

        return isValidText
    }
}


data class WorkWithDrawingUiState(
    private val _drawings: MutableStateFlow<List<Drawing>> = MutableStateFlow(listOf()),
    private val _labels: MutableStateFlow<List<Label>> = MutableStateFlow(listOf()),
    private val _typeOfDefect: MutableStateFlow<List<TypeOfDefect>> = MutableStateFlow(listOf()),
    private val _defectsList: MutableStateFlow<List<Defect>> = MutableStateFlow(listOf()),
    private val _defectPointsList: MutableStateFlow<List<DefectPoint>> = MutableStateFlow(listOf()),
    private val _texts: MutableStateFlow<List<Text>> = MutableStateFlow(listOf()),

    val text: String = "",
    val coordinatesOfText: Pair<Float, Float> = Pair(-1f, -1f),
    val isValidText: Boolean = true,
    val linesForBrokenLine: List<Pair<Offset, Offset>> = listOf(),
    val changesList: ChangesList = ChangesList(),
    val isForwardEnable: Boolean = false,
    val isBackEnable: Boolean = false,
    val typeOfDefect: StateFlow<List<TypeOfDefect>> = _typeOfDefect.asStateFlow(),
    val texts: StateFlow<List<Text>> = _texts.asStateFlow(),
    val drawings: StateFlow<List<Drawing>> = _drawings.asStateFlow(),
    val labels: StateFlow<List<Label>> = _labels.asStateFlow(),
    val defectsList: StateFlow<List<Defect>> = _defectsList.asStateFlow(),
    val defectPointsList: StateFlow<List<DefectPoint>> = _defectPointsList.asStateFlow(),
    var selectedType: TypeOfDefect = TypeOfDefect(),
    val audioNum: Int = 0,
    val photoNum: Int = 0,
    val textSelected: Boolean = false,
    val drawingBrokenLine: Boolean = false,
    val frameSelected: Boolean = false,
    val brokenLineSelected: Boolean = false,
    val lineSegmentSelected: Boolean = false,
    val pointDefectSelected: Boolean = false,
    val photoMode: Boolean = false,
    val swipeMode: Boolean = true,
    val audioMode: Boolean = false,
    val scale: Float = 1f,
    val offsetX: Float = 0f,
    val offsetY: Float = 0f,
    var currentDrawing: Drawing = Drawing()
)