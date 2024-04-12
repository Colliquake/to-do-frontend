package com.example.to_do_frontend.model.data

import android.util.Log
import com.example.to_do_frontend.model.Client
import com.example.to_do_frontend.model.TaskModel
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.runBlocking

class TaskDatasource(val androidId: String) {
    val clientInstance = Client.getInstance()
    val httpClient = clientInstance.getClient()!!       //todo: check if httpClient can actually be !! here
    private lateinit var tasks: ArrayList<TaskModel>
    
    //todo: query params
    
    fun getTasksComplete(){
        runBlocking {
            val httpResponse = httpClient.get("http://3.144.105.116:3000/tasks?completed=true&collectionName=${androidId}")
            tasks = if(httpResponse.status.value == 200){
                Log.v("trace", "get complete tasks")
                httpResponse.body()
            } else{
                Log.e("trace", "get tasks returned status 400")
                arrayListOf<TaskModel>()
            }
        }
    }
    
    fun getTasksIncomplete(){
        runBlocking {
            val httpResponse = httpClient.get("http://3.144.105.116:3000/tasks"){
                url {
                    parameters.append("completed", "false")
                    parameters.append("collectionName", androidId)
                }
            }
            tasks = if(httpResponse.status.value == 200){
                Log.v("trace", "get incomplete tasks")
                httpResponse.body()
            } else{
                Log.e("trace", "get tasks returned status 400")
                arrayListOf<TaskModel>()
            }
        }
    }
    
    fun getTask(taskId: String){
        runBlocking {
            val httpResponse = httpClient.get("http://3.144.105.116:3000/tasks/${taskId}"){
                url {
                    parameters.append("collectionName", androidId)
                }
            }
            tasks = if(httpResponse.status.value == 200){
                Log.v("trace", "getTask")
                arrayListOf(httpResponse.body())
            } else{
                Log.e("trace", "get Task returned status 400")
                arrayListOf<TaskModel>()
            }
        }
    }
    
    fun createTask(newTask: CreateTaskModel){
        runBlocking {
            val httpResponse = httpClient.post("http://3.144.105.116:3000/tasks"){
                url {
                    parameters.append("collectionName", androidId)
                }
                contentType(ContentType.Application.Json)
                setBody(newTask)
            }
            val createTaskResponse = if(httpResponse.status.value == 201){      //todo - not doing anything with the response body yet (i put "todo" to make this easier to see)
                Log.v("trace", "createTask successful")
                arrayListOf(httpResponse.body())
            } else {
                Log.e("trace", "createTask returned status 400")
                arrayListOf<TaskModel>()
            }
        }
    }
    
    fun updateTask(updatedTask: TaskModel){
        runBlocking {
            val httpResponse = httpClient.put("http://3.144.105.116:3000/tasks/${updatedTask.id}"){
                url {
                    parameters.append("collectionName", androidId)
                }
                contentType(ContentType.Application.Json)
                setBody(updatedTask)
            }
            val updateTaskResponse = if(httpResponse.status.value == 200){      //todo - same thing as above
                Log.v("trace", "updateTask successful")
                arrayListOf(httpResponse.body())
            } else{
                Log.e("trace", "updateTask returned status 400")
                arrayListOf<TaskModel>()
            }
        }
    }
    
    //todo: write the delete task endpoint
    
    fun setTasksAll(){
        //todo
    }
    
    fun getTasks(): ArrayList<TaskModel> {
        return tasks
    }
}