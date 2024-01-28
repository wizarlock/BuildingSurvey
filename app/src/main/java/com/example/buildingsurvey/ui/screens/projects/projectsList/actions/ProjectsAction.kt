package com.example.buildingsurvey.ui.screens.projects.projectsList.actions

import com.example.buildingsurvey.data.model.Project

sealed class ProjectsAction {
    data class DeleteProject(val project: Project) : ProjectsAction()
    data class UpdateProject(val project: Project) : ProjectsAction()
}
