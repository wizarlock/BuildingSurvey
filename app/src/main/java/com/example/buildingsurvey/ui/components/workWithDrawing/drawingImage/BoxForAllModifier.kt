package com.example.buildingsurvey.ui.components.workWithDrawing.drawingImage

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import com.example.buildingsurvey.ui.screens.workWithDrawing.actions.WorkWithDrawingAction
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculateCentroid
import androidx.compose.foundation.gestures.calculateCentroidSize
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateRotation
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.positionChanged
import kotlin.math.PI
import kotlin.math.abs

@Composable
fun boxForAllModifier(
    photoMode: Boolean,
    changeZoom: (Boolean) -> Unit,
    uiAction: (WorkWithDrawingAction) -> Unit
): Modifier {
    val defaultBoxModifier = Modifier
        .fillMaxSize()
        .background(Color.White)

    return if (!photoMode) {
        defaultBoxModifier.pointerInput(Unit) {
            myDetectTransformGestures(
                onGestureStart = {
                    changeZoom(true)
                },
                onGesture = { _, pan, zoom, _, _, _ ->
                    uiAction(
                        WorkWithDrawingAction.UpdateScaleAndOffset(
                            pan = pan,
                            zoom = zoom
                        )
                    )
                },
                onGestureEnd = {
                    changeZoom(false)
                }
            )
        }
    } else defaultBoxModifier
}

suspend fun PointerInputScope.myDetectTransformGestures(
    panZoomLock: Boolean = false,
    consume: Boolean = true,
    pass: PointerEventPass = PointerEventPass.Main,
    onGestureStart: (PointerInputChange) -> Unit = {},
    onGesture: (
        centroid: Offset,
        pan: Offset,
        zoom: Float,
        rotation: Float,
        mainPointer: PointerInputChange,
        changes: List<PointerInputChange>
    ) -> Unit,
    onGestureEnd: (PointerInputChange) -> Unit = {}
) {
    awaitEachGesture {
        var rotation = 0f
        var zoom = 1f
        var pan = Offset.Zero
        var pastTouchSlop = false
        val touchSlop = viewConfiguration.touchSlop
        var lockedToPanZoom = false

        val down: PointerInputChange = awaitFirstDown(
            requireUnconsumed = false,
            pass = pass
        )

        var pointer = down
        var pointerId = down.id

        do {
            val event = awaitPointerEvent(pass = pass)

            val canceled =
                event.changes.any { it.isConsumed }

            if (!canceled) {
                val pointerInputChange =
                    event.changes.firstOrNull { it.id == pointerId }
                        ?: event.changes.first()
                pointerId = pointerInputChange.id
                pointer = pointerInputChange

                val zoomChange = event.calculateZoom()
                val rotationChange = event.calculateRotation()
                val panChange = event.calculatePan()

                if (!pastTouchSlop) {
                    zoom *= zoomChange
                    rotation += rotationChange
                    pan += panChange

                    val centroidSize = event.calculateCentroidSize(useCurrent = false)
                    val zoomMotion = abs(1 - zoom) * centroidSize
                    val rotationMotion =
                        abs(rotation * PI.toFloat() * centroidSize / 180f)
                    val panMotion = pan.getDistance()

                    if (zoomMotion > touchSlop ||
                        rotationMotion > touchSlop ||
                        panMotion > touchSlop
                    ) {
                        pastTouchSlop = true
                        lockedToPanZoom = panZoomLock && rotationMotion < touchSlop
                    }
                }

                if (pastTouchSlop) {
                    val centroid = event.calculateCentroid(useCurrent = false)
                    val effectiveRotation = if (lockedToPanZoom) 0f else rotationChange
                    if (effectiveRotation != 0f ||
                        zoomChange != 1f ||
                        panChange != Offset.Zero
                    ) {
                        onGesture(
                            centroid,
                            panChange,
                            zoomChange,
                            effectiveRotation,
                            pointer,
                            event.changes
                        )
                    }

                    if (consume) {
                        event.changes.forEach {
                            if (it.positionChanged()) {
                                it.consume()
                            }
                        }
                    }
                }
            }
            val pressedPointers = event.changes.filter { it.pressed }
            if (pressedPointers.size == 2) {
                onGestureStart(pressedPointers.first())
            } else if (pressedPointers.size < 2) {
                onGestureEnd(pressedPointers.firstOrNull() ?: pointer)
            }
        } while (!canceled && event.changes.any { it.pressed })
    }
}