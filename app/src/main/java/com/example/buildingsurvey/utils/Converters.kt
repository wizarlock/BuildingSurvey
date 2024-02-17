package com.example.buildingsurvey.utils

import com.example.buildingsurvey.data.db.entities.AudioDbEntity
import com.example.buildingsurvey.data.db.entities.DrawingDbEntity
import com.example.buildingsurvey.data.db.entities.LabelDbEntity
import com.example.buildingsurvey.data.db.entities.ProjectDbEntity
import com.example.buildingsurvey.data.db.entities.TypeOfDefectDbEntity
import com.example.buildingsurvey.data.model.Audio
import com.example.buildingsurvey.data.model.Drawing
import com.example.buildingsurvey.data.model.Label
import com.example.buildingsurvey.data.model.Project
import com.example.buildingsurvey.data.model.TypeOfDefect

fun Project.toProjectDbEntity(): ProjectDbEntity =
    ProjectDbEntity(
        id = this.id,
        name = this.name,
        projectFilePath = this.projectFilePath
    )

fun ProjectDbEntity.toProject(): Project =
    Project(
        id = this.id,
        name = this.name,
        projectFilePath = this.projectFilePath
    )

fun Drawing.toDrawingDbEntity(): DrawingDbEntity =
    DrawingDbEntity(
        id = this.id,
        name = this.name,
        drawingFilePath = this.drawingFilePath,
        projectId = this.projectId
    )

fun DrawingDbEntity.toDrawing(): Drawing =
    Drawing(
        id = this.id,
        name = this.name,
        drawingFilePath = this.drawingFilePath,
        projectId = this.projectId
    )

fun Audio.toAudioDbEntity(): AudioDbEntity =
    AudioDbEntity(
        id = this.id,
        name = this.name,
        audioFilePath = this.audioFilePath,
        projectId = this.projectId,
        drawingId = this.drawingId
    )

fun AudioDbEntity.toAudio(): Audio =
    Audio(
        id = this.id,
        name = this.name,
        audioFilePath = this.audioFilePath,
        projectId = this.projectId,
        drawingId = this.drawingId
    )

fun Label.toLabelDbEntity(): LabelDbEntity =
    LabelDbEntity(
        id = this.id,
        name = this.name,
        labelFilePath = this.labelFilePath,
        drawingId = this.drawingId,
        xInApp = this.xInApp,
        yInApp = this.yInApp,
        xReal = this.xReal,
        yReal = this.yReal
    )

fun LabelDbEntity.toLabel(): Label =
    Label(
        id = this.id,
        name = this.name,
        labelFilePath = this.labelFilePath,
        drawingId = this.drawingId,
        xInApp = this.xInApp,
        yInApp = this.yInApp,
        xReal = this.xReal,
        yReal = this.yReal
    )

fun TypeOfDefectDbEntity.toTypeOfDefect(): TypeOfDefect =
    TypeOfDefect(
        id = this.id,
        name = this.name,
        hexCode = this.hexCode,
        projectId = this.projectId
    )

fun TypeOfDefect.toTypeOfDefectDbEntity(): TypeOfDefectDbEntity =
    TypeOfDefectDbEntity(
        id = this.id,
        name = this.name,
        hexCode = this.hexCode,
        projectId = this.projectId
    )