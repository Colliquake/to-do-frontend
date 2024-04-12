package com.example.to_do_frontend.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.to_do_frontend.model.AndroidIdSingleton
import com.example.to_do_frontend.model.TaskModel
import com.example.to_do_frontend.model.data.TaskDatasource

class TaskListViewModel(application: Application) : AndroidViewModel(application) {
    private var _tasksLiveData = MutableLiveData<ArrayList<TaskModel>>()
    val tasksLiveData: LiveData<ArrayList<TaskModel>> get() = _tasksLiveData
    
    private val applicationContext = getApplication<Application>().applicationContext
    val androidId = AndroidIdSingleton.getInstance(applicationContext).getAndroidId()
//    val TaskDatasourceObject = TaskDatasource(androidId!!)
    val TaskDatasourceObject = TaskDatasource("123456")
    
    init {
        getTasksIncomplete()
    }
    
    private fun getTasksComplete(){
        TaskDatasourceObject.getTasksComplete()
        _tasksLiveData.postValue(TaskDatasourceObject.getTasks())
    }
    
    private fun getTasksIncomplete(){
        TaskDatasourceObject.getTasksIncomplete()
        _tasksLiveData.postValue(TaskDatasourceObject.getTasks())
    }
}