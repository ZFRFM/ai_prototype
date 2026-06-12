package com.example.prototype

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PracticeTaskStateTest {
    @Test
    fun blankTaskIsIgnored() {
        val state = PracticeTaskState()
            .addTask("   ")

        assertTrue(state.tasks.isEmpty())
    }

    @Test
    fun validTaskIsAdded() {
        val state = PracticeTaskState()
            .addTask("Read requirements")

        assertEquals(listOf("Read requirements"), state.tasks.map { it.title })
    }

    @Test
    fun addedTaskStartsIncomplete() {
        val state = PracticeTaskState()
            .addTask("Run tests")

        assertFalse(state.tasks.single().isCompleted)
    }

    @Test
    fun toggleChangesCompletionState() {
        val initialState = PracticeTaskState()
            .addTask("Build feature")

        val taskId = initialState.tasks.single().id
        val toggledState = initialState.toggleTask(taskId)

        assertTrue(toggledState.tasks.single().isCompleted)
    }

    @Test
    fun clearCompletedRemovesOnlyCompletedTasks() {
        val initialState = PracticeTaskState()
            .addTask("Keep this")
            .addTask("Remove this")

        val completedTaskId = initialState.tasks.last().id
        val clearedState = initialState
            .toggleTask(completedTaskId)
            .clearCompleted()

        assertEquals(listOf("Keep this"), clearedState.tasks.map { it.title })
    }
}
