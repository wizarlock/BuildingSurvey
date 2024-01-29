package com.example.buildingsurvey.ui.screens.drawings.drawingsList

import androidx.lifecycle.ViewModel
import com.example.buildingsurvey.data.RepositoryInterface
import com.example.buildingsurvey.data.model.Drawing
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DrawingsListViewModel @Inject constructor(
    private val repository: RepositoryInterface,
) : ViewModel() {
    private val _uiState = MutableStateFlow(DrawingsListUiState())
    val uiState = _uiState.asStateFlow()

}

data class DrawingsListUiState(
    val projectName: String = "",
    val drawings: MutableStateFlow<List<Drawing>> = MutableStateFlow(listOf()),
    val audioNum: Int = 0
)