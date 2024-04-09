package com.example.to_do_frontend.model.data.apicalls

import android.util.Log
import com.example.to_do_frontend.model.TaskModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class GetIncompleteTasks(val httpClient: HttpClient) {
    
    suspend fun execute(): ArrayList<TaskModel> {
        val httpResponse = httpClient.get("http://192.168.1.249:3000/tasks?completed=false")
        if(httpResponse.status.value == 200){
            Log.v("trace", "git")
            return httpResponse.body()
        }
        else{
            return arrayListOf<TaskModel>()
        }
    }
    
}