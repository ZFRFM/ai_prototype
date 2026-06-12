package com.example.prototype.ui

import com.example.prototype.domain.TaskDomain

data class TaskUiData(
    val id: Long,
    val title: String,
    val isCompleted: Boolean = false,
)

fun TaskDomain.toUiData(): TaskUiData {
    return TaskUiData(
        id = id,
        title = title,
        isCompleted = isCompleted,
    )
}
