package com.example.prototype.domain

data class TaskDomain(
    val id: Long,
    val title: String,
    val isCompleted: Boolean = false,
)
