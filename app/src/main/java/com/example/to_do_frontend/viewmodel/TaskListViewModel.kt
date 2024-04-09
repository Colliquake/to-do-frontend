package com.example.to_do_frontend.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.to_do_frontend.model.TaskModel
import com.example.to_do_frontend.model.data.TaskDatasource

class TaskListViewModel(private val taskData: TaskDatasource = TaskDatasource("asdf")) : ViewModel() {
    private var _tasksLiveData = MutableLiveData<ArrayList<TaskModel>>()
    val tasksLiveData: LiveData<ArrayList<TaskModel>> get() = _tasksLiveData
    
    val TaskDatasourceObject = TaskDatasource("asdf")
    
    init {
        getTasksIncomplete()
    }
    
    private fun getTasksComplete(){
        TaskDatasourceObject.setTasksComplete()
        _tasksLiveData.postValue(TaskDatasourceObject.getTasks())
    }
    
    private fun getTasksIncomplete(){
        TaskDatasourceObject.setTasksIncomplete()
        _tasksLiveData.postValue(TaskDatasourceObject.getTasks())
    }
    
    fun changeText(){
        getTasksComplete()
    }
}