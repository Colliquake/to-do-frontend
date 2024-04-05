package com.example.to_do_frontend

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val allTasks = getAllTasks()
                Log.v("allTasks", allTasks[0].id)
//                val task = getTask("75f3da26-8f25-41fb-9810-519cd76fa879")
//                findViewById<TextView>(R.id.text_holder).setText(task.id + "\n" + task.taskDescription + "\n" + task.createdDate + "\n" + task.dueDate + "\n" + task.completed)

            }
        }
    }
    
    suspend fun getAllTasks(): Array<Task> {
        val client = HttpClient(Android){
            install(ContentNegotiation){
                json()
            }
        }
        
        val result: Array<Task> = client.get("http://192.168.1.249:3000/tasks?completed=false").body()
        return result
    }
    
    suspend fun getTask(id: String): Task {
        val client = HttpClient(Android){
            install(ContentNegotiation){
                json()
            }
        }
        
        val result: Task = client.get("http://192.168.1.249:3000/tasks/$id").body()
        return result
    }
}