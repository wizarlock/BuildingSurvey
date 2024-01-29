package com.example.buildingsurvey.ui.screens.projects.projectsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buildingsurvey.data.RepositoryInterface
import com.example.buildingsurvey.ui.screens.projects.projectsList.actions.ProjectsAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectsListViewModel @Inject constructor(
    private val repository: RepositoryInterface
) : ViewModel() {

    val projects = repository.projectsList

    fun onUiAction(action: ProjectsAction) {
        when (action) {
            is ProjectsAction.DeleteProject -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.removeProject(action.project)
                }
            }

            is ProjectsAction.UpdateProject -> repository.currentProject = action.project
        }
    }
}