package com.timife.a_n_nursery_app

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.clear
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.map

class UserPreferences(context: Context) {
    private val applicationContext = context.applicationContext
    private val dataStore: DataStore<Preferences>


    init {
        dataStore = applicationContext.createDataStore(
                name = "my_data_store"
        )
    }

    val authToken: kotlinx.coroutines.flow.Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_AUTH]
        }

    suspend fun saveAuthToken(authToken: String) {
        dataStore.edit { preferences ->
            preferences[KEY_AUTH] = authToken
        }
    }
    suspend fun clear(){
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
    companion object {
        private val KEY_AUTH = preferencesKey<String>("key_auth")
    }
}