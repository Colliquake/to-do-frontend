package com.example.to_do_frontend.view

import com.example.to_do_frontend.model.TaskModel

interface OnCheckedChangeListener {
    fun onItemCheckedChange(task: TaskModel);
}