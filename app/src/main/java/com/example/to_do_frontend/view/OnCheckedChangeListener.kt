package com.example.to_do_frontend.view

import com.example.to_do_frontend.model.TaskModel

interface OnClickedChangeListener {
    fun onItemCheckedChange(task: TaskModel);
    
    fun onDeleteClickedChange(taskId: String);
}