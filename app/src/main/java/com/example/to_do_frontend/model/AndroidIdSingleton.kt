package com.example.to_do_frontend.model

import android.content.Context
import android.provider.Settings

class AndroidIdSingleton private constructor(context: Context) {
    private var androidId: String? = null
    
    init {
        androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }
    
    companion object {
        @Volatile
        private var INSTANCE: AndroidIdSingleton? = null
        
        fun getInstance(context: Context): AndroidIdSingleton {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AndroidIdSingleton(context.applicationContext).also { INSTANCE = it}
            }
        }
    }
    
    fun getAndroidId(): String? {
        return androidId
    }
}


//      val androidId = AndroidIdSingleton.getInstance(applicationContext).getAndroidId()