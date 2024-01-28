package com.example.buildingsurvey.ui.components.projects.projectsList

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

import com.example.buildingsurvey.data.model.Project

@Composable
fun ListOfProjects(
    projects: List<Project>,
    onCardClick: (Project) -> Unit,
    onDeleteClick: (Project) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(all = 16.dp)
    ) {

        items(
            items = projects,
            key = {
                it.id
            }
        ) { project ->
            ProjectCard(
                project = project,
                onCardClick = { onCardClick(project) },
                onDeleteClick = { onDeleteClick(project) }
            )
        }
    }
}
