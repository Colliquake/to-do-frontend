package com.example.to_do_frontend.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.to_do_frontend.model.AndroidIdSingleton
import com.example.to_do_frontend.model.TaskModel
import com.example.to_do_frontend.model.data.TaskDatasource
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class EditViewModel(application: Application) : AndroidViewModel(application) {
    private var _task = MutableLiveData<ArrayList<TaskModel>>()
    val task: LiveData<ArrayList<TaskModel>> get() = _task
    
    private val applicationContext = getApplication<Application>().applicationContext
    val androidId = AndroidIdSingleton.getInstance(applicationContext).getAndroidId()
    val TaskDatasourceObject = TaskDatasource(androidId!!)
    
    fun updateTask(updatedTask: TaskModel) {
        TaskDatasourceObject.updateTask(updatedTask)
    }
    
    fun formatDueDate(dueDate: String): String {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy HH:mm:ss")
        val zdt: ZonedDateTime = ZonedDateTime.parse(
            dueDate + " 00:00:00", formatter.withZone(
                ZoneId.systemDefault()
            )
        )
        val str = zdt.format(DateTimeFormatter.ISO_INSTANT)
        
        return str
    }
    
    fun getDateFromInstant(instant: Instant): String {
        val zdt = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC)
        val month = zdt.month.toString().lowercase().replaceFirstChar(Char::uppercaseChar)
        val day = zdt.dayOfMonth.toString()
        val year = zdt.year.toString()
        
        return "${month} ${day}, ${year}"
    }
    
    fun getTask(taskId: String) {
        TaskDatasourceObject.getTask(taskId)
        _task.postValue(TaskDatasourceObject.getTasksResult())
    }
}

class EditViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
