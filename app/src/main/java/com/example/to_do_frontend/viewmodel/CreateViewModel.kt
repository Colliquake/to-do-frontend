package com.example.to_do_frontend.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.to_do_frontend.model.AndroidIdSingleton
import com.example.to_do_frontend.model.CreateTaskModel
import com.example.to_do_frontend.model.data.TaskDatasource
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class CreateViewModel(application: Application) : AndroidViewModel(application) {
    private val applicationContext = getApplication<Application>().applicationContext
    val androidId = AndroidIdSingleton.getInstance(applicationContext).getAndroidId()
    val TaskDatasourceObject = TaskDatasource(androidId!!)
    
    private fun createTask(task: CreateTaskModel) {
        TaskDatasourceObject.createTask(task)
    }
    
    fun createTask(taskDescription: String, dueDate: String, completed: Boolean){
        val task = CreateTaskModel(
            taskDescription,
            dueDate,
            completed
        )
        createTask(task)
    }
    
    fun formatDueDate(dueDate: String): String{
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy HH:mm:ss")
        val zdt: ZonedDateTime = ZonedDateTime.parse(dueDate + " 00:00:00", formatter.withZone(
            ZoneId.systemDefault()))
        val str = zdt.format(DateTimeFormatter.ISO_INSTANT)
        
        return str
    }
}

class CreateViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreateViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}