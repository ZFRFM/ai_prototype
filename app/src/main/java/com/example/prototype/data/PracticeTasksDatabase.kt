package com.example.prototype.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [TaskData::class],
    version = 1,
    exportSchema = false,
)
abstract class PracticeTasksDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        fun create(context: Context): PracticeTasksDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                PracticeTasksDatabase::class.java,
                "practice_tasks.db",
            ).build()
        }
    }
}
