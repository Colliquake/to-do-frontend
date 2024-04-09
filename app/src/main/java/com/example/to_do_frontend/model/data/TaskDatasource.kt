package com.example.to_do_frontend.model.data

import com.example.to_do_frontend.model.Client
import com.example.to_do_frontend.model.TaskModel
import com.example.to_do_frontend.model.data.apicalls.GetCompleteTasks
import com.example.to_do_frontend.model.data.apicalls.GetIncompleteTasks
import kotlinx.coroutines.runBlocking

class TaskDatasource(val androidId: String) {
    val clientInstance = Client.getInstance()
    val httpClient = clientInstance.getClient()
    private lateinit var tasks: ArrayList<TaskModel>
    
    //todo: query params
    
    fun setTasksComplete(){
        runBlocking {
            val GCTclass = GetCompleteTasks(httpClient!!)
            tasks = GCTclass.execute()
        }
    }
    
    fun setTasksIncomplete(){
        runBlocking {
            val GITclass = GetIncompleteTasks(httpClient!!)
            tasks = GITclass.execute()
        }
    }
    
    fun setTasksAll(){
        //todo
    }
    
    fun getTasks(): ArrayList<TaskModel> {
        return tasks
    }
}