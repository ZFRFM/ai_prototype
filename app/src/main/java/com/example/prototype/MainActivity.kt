package com.example.prototype

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.prototype.data.PracticeTasksDatabase
import com.example.prototype.data.RoomTaskRepository
import com.example.prototype.domain.ClearCompletedTasksUseCase
import com.example.prototype.domain.GetTasksUseCase
import com.example.prototype.domain.InsertTaskUseCase
import com.example.prototype.domain.SetTaskCompletedUseCase
import com.example.prototype.ui.PracticeTasksApp
import com.example.prototype.ui.theme.PrototypeTheme

class MainActivity : ComponentActivity() {
    private val database by lazy { PracticeTasksDatabase.create(this) }
    private val repository by lazy { RoomTaskRepository(database.taskDao()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val getTasksUseCase = GetTasksUseCase(repository)
        val insertTaskUseCase = InsertTaskUseCase(repository)
        val setTaskCompletedUseCase = SetTaskCompletedUseCase(repository)
        val clearCompletedTasksUseCase = ClearCompletedTasksUseCase(repository)

        enableEdgeToEdge()
        setContent {
            PrototypeTheme {
                Scaffold { innerPadding ->
                    PracticeTasksApp(
                        getTasksUseCase = getTasksUseCase,
                        insertTaskUseCase = insertTaskUseCase,
                        setTaskCompletedUseCase = setTaskCompletedUseCase,
                        clearCompletedTasksUseCase = clearCompletedTasksUseCase,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
