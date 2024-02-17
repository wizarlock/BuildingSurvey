package com.example.buildingsurvey.ui.screens.addDefect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buildingsurvey.data.RepositoryInterface
import com.example.buildingsurvey.data.model.TypeOfDefect
import com.example.buildingsurvey.ui.screens.addDefect.actions.AddDefectAction
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
class AddDefectViewModel @Inject constructor(
    private val repository: RepositoryInterface
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddDefectUiState())
    val uiState = _uiState.asStateFlow()

    fun onUiAction(action: AddDefectAction) {
        when (action) {
            is AddDefectAction.UpdateDefectName -> _uiState.update {
                uiState.value.copy(defectName = action.name)
            }

            is AddDefectAction.UpdateDefectColorHexCode -> _uiState.update {
                uiState.value.copy(defectColorHexCode = action.hexCode)
            }

            AddDefectAction.SaveDefect -> {
                val typeOfDefect = TypeOfDefect(
                    name = uiState.value.defectName,
                    hexCode = uiState.value.defectColorHexCode,
                    projectId = repository.currentProject.id
                )
                viewModelScope.launch(Dispatchers.IO) {
                    repository.addTypeOfDefect(
                        typeOfDefect = typeOfDefect
                    )
                }
            }
        }
    }

    fun areFieldsValid(): Boolean {
        val isValidDefectName = isValidName(
            name = uiState.value.defectName,
        )

        val isNotRepeatDefectName = isNotRepeatName(
            name = uiState.value.defectName,
            list = repository.typeOfDefectList.value.filter { it.projectId == repository.currentProject.id }.map { it.name }
        ) &&  uiState.value.defectName != "0"

        val isValidDefectColorHexCode = uiState.value.defectColorHexCode.isNotEmpty()

        val isNotRepeatDefectColorHexCode = isNotRepeatName(
            name = uiState.value.defectColorHexCode,
            list = repository.typeOfDefectList.value.filter { it.projectId == repository.currentProject.id }.map { it.hexCode }
        ) &&  uiState.value.defectColorHexCode != "FF000000"
        _uiState.update {
            uiState.value.copy(
                isValidDefectName = isValidDefectName,
                isNotRepeatDefectName = isNotRepeatDefectName,
                isValidDefectColorHexCode = isValidDefectColorHexCode,
                isNotRepeatDefectColorHexCode = isNotRepeatDefectColorHexCode
            )
        }

        return isValidDefectName && isNotRepeatDefectName && isValidDefectColorHexCode && isNotRepeatDefectColorHexCode
    }
}

data class AddDefectUiState(
    val defectName: String = "",
    val isValidDefectName: Boolean = true,
    val isNotRepeatDefectName: Boolean = true,
    val defectColorHexCode: String = "",
    val isValidDefectColorHexCode: Boolean = true,
    val isNotRepeatDefectColorHexCode: Boolean = true
)