package com.example.to_do_frontend.model.data

import android.util.Log
import com.example.to_do_frontend.model.Client
import com.example.to_do_frontend.model.TaskModel
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.runBlocking

class TaskDatasource(val androidId: String) {
    val clientInstance = Client.getInstance()
    val httpClient = clientInstance.getClient()
    private lateinit var tasks: ArrayList<TaskModel>
    
    //todo: query params
    
    fun setTasksComplete(){
        runBlocking {
            val httpResponse = httpClient!!.get("http://3.144.105.116:3000/tasks?completed=true&collectionName=${androidId}")
            tasks = if(httpResponse.status.value == 200){
                Log.v("trace", "get complete tasks")
                httpResponse.body()
            } else{
                Log.e("trace", "get tasks returned status 400")
                arrayListOf<TaskModel>()
            }
        }
    }
    
    fun setTasksIncomplete(){
        runBlocking {
            val httpResponse = httpClient!!.get("http://3.144.105.116:3000/tasks?completed=false&collectionName=${androidId}")
            tasks = if(httpResponse.status.value == 200){
                Log.v("trace", "get incomplete tasks")
                httpResponse.body()
            } else{
                Log.e("trace", "get tasks returned status 400")
                arrayListOf<TaskModel>()
            }
        }
    }
    
    fun setTasksAll(){
        //todo
    }
    
    fun getTasks(): ArrayList<TaskModel> {
        return tasks
    }
}