package com.example.prototype.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.prototype.domain.TaskDomain

@Entity(tableName = "tasks")
data class TaskData(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val isCompleted: Boolean = false,
)

fun TaskData.toDomain(): TaskDomain {
    return TaskDomain(
        id = id,
        title = title,
        isCompleted = isCompleted,
    )
}

fun TaskDomain.toData(): TaskData {
    return TaskData(
        id = id,
        title = title,
        isCompleted = isCompleted,
    )
}
