package com.example.prototype.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.prototype.domain.ClearCompletedTasksUseCase
import com.example.prototype.domain.GetTasksUseCase
import com.example.prototype.domain.InsertTaskUseCase
import com.example.prototype.domain.SetTaskCompletedUseCase
import com.example.prototype.ui.theme.PrototypeTheme
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun PracticeTasksApp(
    getTasksUseCase: GetTasksUseCase,
    insertTaskUseCase: InsertTaskUseCase,
    setTaskCompletedUseCase: SetTaskCompletedUseCase,
    clearCompletedTasksUseCase: ClearCompletedTasksUseCase,
    modifier: Modifier = Modifier,
) {
    val taskUiDataFlow = remember(getTasksUseCase) {
        getTasksUseCase().map { taskDomains ->
            taskDomains.map { taskDomain -> taskDomain.toUiData() }
        }
    }
    val tasks by taskUiDataFlow.collectAsState(initial = emptyList())
    var taskInput by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    PracticeTasksScreen(
        tasks = tasks,
        taskInput = taskInput,
        onTaskInputChange = { taskInput = it },
        onAddTask = {
            val title = taskInput
            taskInput = ""
            coroutineScope.launch {
                insertTaskUseCase(title)
            }
        },
        onTaskCompletionChange = { taskId, isCompleted ->
            coroutineScope.launch {
                setTaskCompletedUseCase(taskId, isCompleted)
            }
        },
        onClearCompleted = {
            coroutineScope.launch {
                clearCompletedTasksUseCase()
            }
        },
        modifier = modifier,
    )
}

@Composable
fun PracticeTasksScreen(
    tasks: List<TaskUiData>,
    taskInput: String,
    onTaskInputChange: (String) -> Unit,
    onAddTask: () -> Unit,
    onTaskCompletionChange: (Long, Boolean) -> Unit,
    onClearCompleted: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "Practice Tasks",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            OutlinedTextField(
                value = taskInput,
                onValueChange = onTaskInputChange,
                label = { Text("Task name") },
                singleLine = true,
                modifier = Modifier.weight(1f),
            )

            Button(
                onClick = onAddTask,
                enabled = taskInput.isNotBlank(),
            ) {
                Text("Add")
            }
        }

        if (tasks.isEmpty()) {
            Text(
                text = "No practice tasks yet",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f, fill = false),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(
                    items = tasks,
                    key = { task -> task.id },
                ) { task ->
                    PracticeTaskRow(
                        task = task,
                        onTaskCompletionChange = onTaskCompletionChange,
                    )
                }
            }
        }

        Button(
            onClick = onClearCompleted,
            enabled = tasks.any { it.isCompleted },
        ) {
            Text("Clear completed")
        }
    }
}

@Composable
fun PracticeTaskRow(
    task: TaskUiData,
    onTaskCompletionChange: (Long, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = { isCompleted ->
                onTaskCompletionChange(task.id, isCompleted)
            },
        )
        Text(
            text = task.title,
            style = MaterialTheme.typography.bodyLarge,
            color = if (task.isCompleted) {
                MaterialTheme.colorScheme.onSurfaceVariant
            } else {
                MaterialTheme.colorScheme.onSurface
            },
            textDecoration = if (task.isCompleted) {
                TextDecoration.LineThrough
            } else {
                TextDecoration.None
            },
            modifier = Modifier.weight(1f),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PracticeTasksPreview() {
    PrototypeTheme {
        PracticeTasksScreen(
            tasks = listOf(
                TaskUiData(
                    id = 1L,
                    title = "Read requirements",
                    isCompleted = true,
                ),
                TaskUiData(
                    id = 2L,
                    title = "Run Android CI",
                    isCompleted = false,
                ),
            ),
            taskInput = "Write tests",
            onTaskInputChange = {},
            onAddTask = {},
            onTaskCompletionChange = { _, _ -> },
            onClearCompleted = {},
        )
    }
}
