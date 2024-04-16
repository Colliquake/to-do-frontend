package com.example.to_do_frontend.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateTaskModel(
    val taskDescription: String,
    val dueDate: String,
    val completed: Boolean
)
