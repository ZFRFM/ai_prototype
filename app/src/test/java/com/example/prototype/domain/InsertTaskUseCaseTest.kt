package com.example.prototype.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class InsertTaskUseCaseTest {
    private val repository = FakeTaskRepository()
    private val useCase = InsertTaskUseCase(repository)

    @Test
    fun blankTaskIsIgnored() = runBlocking {
        useCase("   ")

        assertEquals(emptyList<String>(), repository.insertedTitles)
    }

    @Test
    fun titleIsTrimmedBeforeInsert() = runBlocking {
        useCase("  Read requirements  ")

        assertEquals(listOf("Read requirements"), repository.insertedTitles)
    }

    private class FakeTaskRepository : TaskRepository {
        val insertedTitles = mutableListOf<String>()

        override fun observeTasks(): Flow<List<TaskDomain>> {
            return emptyFlow()
        }

        override suspend fun insertTask(title: String) {
            insertedTitles += title
        }

        override suspend fun setTaskCompleted(id: Long, isCompleted: Boolean) = Unit

        override suspend fun clearCompletedTasks() = Unit
    }
}
