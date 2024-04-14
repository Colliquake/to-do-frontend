package com.example.to_do_frontend.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.to_do_frontend.model.AndroidIdSingleton
import com.example.to_do_frontend.model.TaskModel
import com.example.to_do_frontend.model.TaskParameters
import com.example.to_do_frontend.model.TaskParametersRepository
import com.example.to_do_frontend.model.data.TaskDatasource
import kotlinx.coroutines.launch

class TaskListViewModel(application: Application, private val taskDataStore: TaskParametersRepository) : AndroidViewModel(application) {
    private var _tasksLiveData = MutableLiveData<ArrayList<TaskModel>>()
    val tasksLiveData: LiveData<ArrayList<TaskModel>> get() = _tasksLiveData
    
    private var _tasksParams = taskDataStore.getTaskParameters().asLiveData()
    val tasksParams: LiveData<TaskParameters> get() = _tasksParams
    
    private val applicationContext = getApplication<Application>().applicationContext
    val androidId = AndroidIdSingleton.getInstance(applicationContext).getAndroidId()
    val TaskDatasourceObject = TaskDatasource(androidId!!)
    
    init {
    }
    
    private fun getTasks(params: TaskParameters){
        TaskDatasourceObject.getTasksWithParams(params)
        _tasksLiveData.postValue(TaskDatasourceObject.getTasksResult())
    }
    
    fun changeTasks(params: TaskParameters){
        getTasks(params)
    }
    
    fun changeFilter(filter: String){
        viewModelScope.launch{
            taskDataStore.setFilters(filter)
        }
    }
}

class TaskListViewModelFactory(
    private val application: Application,
    private val taskParametersRepository: TaskParametersRepository
) : ViewModelProvider.Factory {
    
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TaskListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskListViewModel(application, taskParametersRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}