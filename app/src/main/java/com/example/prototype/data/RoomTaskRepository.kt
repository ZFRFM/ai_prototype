package com.example.prototype.data

import com.example.prototype.domain.TaskDomain
import com.example.prototype.domain.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomTaskRepository(
    private val taskDao: TaskDao,
) : TaskRepository {
    override fun observeTasks(): Flow<List<TaskDomain>> {
        return taskDao.observeTasks()
            .map { tasks -> tasks.map(TaskData::toDomain) }
    }

    override suspend fun insertTask(title: String) {
        taskDao.insertTask(TaskData(title = title))
    }

    override suspend fun setTaskCompleted(id: Long, isCompleted: Boolean) {
        taskDao.setTaskCompleted(id, isCompleted)
    }

    override suspend fun clearCompletedTasks() {
        taskDao.clearCompletedTasks()
    }
}
