package com.example.to_do_frontend.model

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

class Client private constructor() {
    private var client: HttpClient? = null
    
    init {
        client = HttpClient(Android){
            install(ContentNegotiation){
                json()
            }
        }
    }
    
    companion object {
        @Volatile
        private var instance: Client? = null
        
        fun getInstance(): Client {
            return instance ?: synchronized(this){
                instance ?: Client().also {instance = it}
            }
        }
    }
    
    fun getClient(): HttpClient? {
        return client
    }
}