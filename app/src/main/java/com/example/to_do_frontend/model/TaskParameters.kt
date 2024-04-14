package com.example.to_do_frontend.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable

@Serializable
data class TaskParameters(
    val filters: String,
    val sort_by: String,
    val sort_date_direction: String
)

class TaskParametersRepository(private val dataStore: DataStore<Preferences>){
    private object PreferencesKeys {
        val FILTERS = stringPreferencesKey("filters")
        val SORT_BY = stringPreferencesKey("sort_by")
        val SORT_DATE_DIRECTION = stringPreferencesKey("sort_date_direction")
    }
    
    fun getTaskParameters(): Flow<TaskParameters> {
        return dataStore.data.map{ preferences ->
            val filters = preferences[PreferencesKeys.FILTERS] ?: "all"
            val sortBy = preferences[PreferencesKeys.SORT_BY] ?: ""
            val sortDateDirection = preferences[PreferencesKeys.SORT_DATE_DIRECTION] ?: "+"
            
            TaskParameters(filters, sortBy, sortDateDirection)
        }
    }
    
    suspend fun setFilters(filter: String){
        dataStore.edit{ preferences ->
            preferences[PreferencesKeys.FILTERS] = filter
        }
    }
    
    suspend fun setSortBy(sortBy: String){
        dataStore.edit{ preferences ->
            preferences[PreferencesKeys.SORT_BY] = sortBy
        }
    }
    
    suspend fun setSortDateDirection(sortDateDirection: String){
        dataStore.edit{ preferences ->
            preferences[PreferencesKeys.SORT_DATE_DIRECTION] = sortDateDirection
        }
    }
}

//filters: "all", "true", or "false"
//sort_by: "dueDate", "createdDate", or ""
//sort_date_direction: "+" or "-"