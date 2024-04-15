package com.example.to_do_frontend.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.to_do_frontend.model.TaskParameters
import com.example.to_do_frontend.model.TaskParametersRepository
import kotlinx.coroutines.launch

class SettingsViewModel(private val taskDataStore: TaskParametersRepository) : ViewModel() {
    private var _tasksParams = taskDataStore.getTaskParameters().asLiveData()
    val tasksParams: LiveData<TaskParameters> get() = _tasksParams
    
    fun changeFilter(filter: String){
        viewModelScope.launch{
            taskDataStore.setFilters(filter)
        }
    }
    
    fun changeSortBy(sortBy: String){
        viewModelScope.launch{
            taskDataStore.setSortBy(sortBy)
        }
    }
    
    fun changeSortDateDirection(sortDateDirection: String){
        viewModelScope.launch{
            taskDataStore.setSortDateDirection(sortDateDirection)
        }
    }
}

class SettingsViewModelFactory(
    private val taskParametersRepository: TaskParametersRepository
) : ViewModelProvider.Factory{
    
    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(taskParametersRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}