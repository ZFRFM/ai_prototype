package com.example.prototype

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.prototype.ui.theme.PrototypeTheme

private val PracticeTaskRowBackground = Color(0xFFE3F2FD)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrototypeTheme {
                Scaffold { innerPadding ->
                    PracticeTasksApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PracticeTasksApp(modifier: Modifier = Modifier) {
    var taskState by remember { mutableStateOf(PracticeTaskState()) }
    var taskInput by remember { mutableStateOf("") }

    PracticeTasksScreen(
        state = taskState,
        taskInput = taskInput,
        onTaskInputChange = { taskInput = it },
        onAddTask = {
            taskState = taskState.addTask(taskInput)
            taskInput = ""
        },
        onToggleTask = { taskId ->
            taskState = taskState.toggleTask(taskId)
        },
        onClearCompleted = {
            taskState = taskState.clearCompleted()
        },
        modifier = modifier,
    )
}

@Composable
fun PracticeTasksScreen(
    state: PracticeTaskState,
    taskInput: String,
    onTaskInputChange: (String) -> Unit,
    onAddTask: () -> Unit,
    onToggleTask: (Int) -> Unit,
    onClearCompleted: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "Practice Tasks",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
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

        if (state.tasks.isEmpty()) {
            Text(
                text = "No practice tasks yet",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(
                    items = state.tasks,
                    key = { task -> task.id },
                ) { task ->
                    PracticeTaskRow(
                        task = task,
                        onToggleTask = onToggleTask,
                    )
                }
            }
        }

        Button(
            onClick = onClearCompleted,
            enabled = state.tasks.any { it.isCompleted },
        ) {
            Text("Clear completed")
        }
    }
}

@Composable
fun PracticeTaskRow(
    task: PracticeTask,
    onToggleTask: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(PracticeTaskRowBackground)
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = { onToggleTask(task.id) },
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
            state = PracticeTaskState()
                .addTask("Read requirements")
                .addTask("Run Android CI")
                .toggleTask(1),
            taskInput = "Write tests",
            onTaskInputChange = {},
            onAddTask = {},
            onToggleTask = {},
            onClearCompleted = {},
        )
    }
}
