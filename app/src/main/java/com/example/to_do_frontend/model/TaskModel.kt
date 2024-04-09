package com.example.to_do_frontend.model

import kotlinx.serialization.Serializable

@Serializable
data class TaskModel(
    val _id: String,
    val id: String,
    var taskDescription: String,
    val createdDate: String,
    var dueDate: String,
    var completed: Boolean,
)
