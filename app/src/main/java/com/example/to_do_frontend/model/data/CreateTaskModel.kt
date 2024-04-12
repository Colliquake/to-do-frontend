package com.example.to_do_frontend.model.data

import kotlinx.serialization.Serializable

@Serializable
data class CreateTaskModel(
    val taskDescription: String,
    val createdDate: String,
    val dueDate: String,
)
