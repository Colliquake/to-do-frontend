package com.example.to_do_frontend.model.data.apicalls

import android.util.Log
import com.example.to_do_frontend.model.TaskModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class GetCompleteTasks(val httpClient: HttpClient) {
    
    suspend fun execute(): ArrayList<TaskModel> {
        val httpResponse = httpClient.get("http://192.168.1.249:3000/tasks?completed=true")
        if(httpResponse.status.value == 200){
            Log.v("trace", "gct")
            return httpResponse.body()
        }
        else{
            return arrayListOf<TaskModel>()
        }
    }
    
}