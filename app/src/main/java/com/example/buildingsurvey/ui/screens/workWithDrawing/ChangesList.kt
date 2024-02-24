package com.example.buildingsurvey.ui.screens.workWithDrawing

class ChangesList {
    private val arr = mutableListOf<Any>()
    private var top = -1

    fun add(value: Any) {
        if (top != arr.size - 1)
            arr.subList(top + 1, arr.size).clear()
        top++
        arr.add(value)
    }

    fun back(): Any {
        val obj = arr[top]
        top--
        return obj
    }

    fun forward(): Any {
        top++
        return arr[top]
    }

    fun backIsAvailable(): Boolean {
        return top != -1
    }

    fun forwardIsAvailable(): Boolean {
        return top != arr.size - 1
    }
}