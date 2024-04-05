package com.example.to_do_frontend

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: String,
    var taskDescription: String,
    val createdDate: String,
    var dueDate: String,
    var completed: Boolean,
    val _id: String
)
