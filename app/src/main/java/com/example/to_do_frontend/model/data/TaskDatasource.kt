package com.example.to_do_frontend.model.data

import android.util.Log
import com.example.to_do_frontend.model.Client
import com.example.to_do_frontend.model.CreateTaskModel
import com.example.to_do_frontend.model.TaskModel
import com.example.to_do_frontend.model.TaskParameters
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.runBlocking

class TaskDatasource(val androidId: String) {
    val clientInstance = Client.getInstance()
    val httpClient =
        clientInstance.getClient()!!
    private lateinit var tasks: ArrayList<TaskModel>
    private val serverAddress = "http://3.144.105.116:3000/"
    
    fun getTasksWithParams(params: TaskParameters) {
        val sortBy = if (params.sort_by == "") {
            ""
        } else {
            params.sort_date_direction + params.sort_by
        }
        
        runBlocking {
            val httpResponse = httpClient.get(serverAddress + "/tasks") {
                url {
                    parameters.append("collectionName", androidId)
                    parameters.append("completed", params.filters)
                    if (sortBy != "") {
                        parameters.append("sort_by", sortBy)
                    }
                }
            }
            tasks = if (httpResponse.status.value == 200) {
                Log.v("trace", "getTasksWithParams successful")
                httpResponse.body()
            } else {
                Log.e("trace", "getTasksWithParams returned status 400")
                arrayListOf<TaskModel>()
            }
        }
    }
    
    fun getTask(taskId: String) {
        runBlocking {
            val httpResponse = httpClient.get(serverAddress + "/tasks/${taskId}") {
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
    
    fun createTask(newTask: CreateTaskModel) {
        runBlocking {
            val httpResponse = httpClient.post(serverAddress + "/tasks") {
                url {
                    parameters.append("collectionName", androidId)
                }
                contentType(ContentType.Application.Json)
                setBody(newTask)
            }
            val createTaskResponse =
                if (httpResponse.status.value == 201) {
                    Log.v("trace", "createTask successful")
                    arrayListOf(httpResponse.body())
                } else {
                    Log.e("trace", "createTask returned status 400")
                    arrayListOf<TaskModel>()
                }
        }
    }
    
    fun updateTask(updatedTask: TaskModel) {
        runBlocking {
            val httpResponse = httpClient.put(serverAddress + "/tasks/${updatedTask.id}") {
                url {
                    parameters.append("collectionName", androidId)
                }
                contentType(ContentType.Application.Json)
                setBody(updatedTask)
            }
            val updateTaskResponse =
                if (httpResponse.status.value == 200) {
                    Log.v("trace", "updateTask successful")
                    arrayListOf(httpResponse.body())
                } else {
                    Log.e("trace", "updateTask returned status 400")
                    arrayListOf<TaskModel>()
                }
        }
    }
    
    fun deleteTask(taskId: String) {
        runBlocking {
            val httpResponse = httpClient.delete(serverAddress + "/tasks/${taskId}") {
                url {
                    parameters.append("collectionName", androidId)
                }
            }
            if (httpResponse.status.value == 200) {
                Log.v("trace", "deleteTask successful")
            } else {
                Log.e("trace", "deleteTask returned status 400")
            }
        }
    }
    
    fun getTasksResult(): ArrayList<TaskModel> {
        return tasks
    }
}