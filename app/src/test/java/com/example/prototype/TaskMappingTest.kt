package com.example.prototype

import com.example.prototype.data.TaskData
import com.example.prototype.data.toDomain
import com.example.prototype.data.toData
import com.example.prototype.domain.TaskDomain
import com.example.prototype.ui.toUiData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class TaskMappingTest {
    @Test
    fun taskDataMapsToDomainAndUiModels() {
        val taskData = TaskData(
            id = 7L,
            title = "Run Android CI",
            isCompleted = true,
        )

        val taskDomain = taskData.toDomain()
        val taskUiData = taskDomain.toUiData()

        assertEquals(7L, taskDomain.id)
        assertEquals("Run Android CI", taskDomain.title)
        assertTrue(taskDomain.isCompleted)
        assertEquals(taskDomain.id, taskUiData.id)
        assertEquals(taskDomain.title, taskUiData.title)
        assertEquals(taskDomain.isCompleted, taskUiData.isCompleted)
    }

    @Test
    fun taskDomainMapsBackToDataModel() {
        val taskDomain = TaskDomain(
            id = 9L,
            title = "Open pull request",
            isCompleted = false,
        )

        val taskData = taskDomain.toData()

        assertEquals(9L, taskData.id)
        assertEquals("Open pull request", taskData.title)
        assertEquals(false, taskData.isCompleted)
    }
}
