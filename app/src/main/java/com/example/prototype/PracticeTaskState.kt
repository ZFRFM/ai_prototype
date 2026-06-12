package com.example.prototype

data class PracticeTask(
    val id: Int,
    val title: String,
    val isCompleted: Boolean = false,
)

data class PracticeTaskState(
    val tasks: List<PracticeTask> = emptyList(),
    private val nextId: Int = 1,
) {
    fun addTask(title: String): PracticeTaskState {
        val trimmedTitle = title.trim()
        if (trimmedTitle.isBlank()) return this

        return copy(
            tasks = tasks + PracticeTask(
                id = nextId,
                title = trimmedTitle,
            ),
            nextId = nextId + 1,
        )
    }

    fun toggleTask(taskId: Int): PracticeTaskState {
        return copy(
            tasks = tasks.map { task ->
                if (task.id == taskId) {
                    task.copy(isCompleted = !task.isCompleted)
                } else {
                    task
                }
            },
        )
    }

    fun clearCompleted(): PracticeTaskState {
        return copy(tasks = tasks.filterNot { it.isCompleted })
    }
}
