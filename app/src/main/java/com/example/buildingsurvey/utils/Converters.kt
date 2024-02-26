package com.example.buildingsurvey.utils

import com.example.buildingsurvey.data.db.entities.AudioDbEntity
import com.example.buildingsurvey.data.db.entities.DefectDbEntity
import com.example.buildingsurvey.data.db.entities.DefectPointDbEntity
import com.example.buildingsurvey.data.db.entities.DrawingDbEntity
import com.example.buildingsurvey.data.db.entities.LabelDbEntity
import com.example.buildingsurvey.data.db.entities.ProjectDbEntity
import com.example.buildingsurvey.data.db.entities.TextDbEntity
import com.example.buildingsurvey.data.db.entities.TypeOfDefectDbEntity
import com.example.buildingsurvey.data.model.Audio
import com.example.buildingsurvey.data.model.Defect
import com.example.buildingsurvey.data.model.DefectPoint
import com.example.buildingsurvey.data.model.Drawing
import com.example.buildingsurvey.data.model.Label
import com.example.buildingsurvey.data.model.Project
import com.example.buildingsurvey.data.model.Text
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

fun Defect.toDefectDbEntity(): DefectDbEntity =
    DefectDbEntity(
        id = this.id,
        drawingId = this.drawingId,
        isClosed = this.isClosed,
        hexCode = this.hexCode
    )

fun DefectDbEntity.toDefect(): Defect =
    Defect(
        id = this.id,
        drawingId = this.drawingId,
        isClosed = this.isClosed,
        hexCode = this.hexCode
    )

fun DefectPoint.toDefectPointDbEntity(): DefectPointDbEntity =
    DefectPointDbEntity(
        id = this.id,
        defectId = this.defectId,
        drawingId = this.drawingId,
        position = this.position,
        xInApp = this.xInApp,
        yInApp = this.yInApp,
        xReal = this.xReal,
        yReal = this.yReal
    )

fun DefectPointDbEntity.toDefectPoint(): DefectPoint =
    DefectPoint(
        id = this.id,
        defectId = this.defectId,
        drawingId = this.drawingId,
        position = this.position,
        xInApp = this.xInApp,
        yInApp = this.yInApp,
        xReal = this.xReal,
        yReal = this.yReal
    )

fun Text.toTextDbEntity(): TextDbEntity =
    TextDbEntity(
        id = this.id,
        text = this.text,
        drawingId = this.drawingId,
        xInApp = this.xInApp,
        yInApp = this.yInApp,
        xReal = this.xReal,
        yReal = this.yReal
    )

fun TextDbEntity.toText(): Text =
    Text(
        id = this.id,
        text = this.text,
        drawingId = this.drawingId,
        xInApp = this.xInApp,
        yInApp = this.yInApp,
        xReal = this.xReal,
        yReal = this.yReal
    )