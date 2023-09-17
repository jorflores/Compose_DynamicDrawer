package com.example.navdrawer.dataStore

import android.content.Context
import android.util.Log

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext


const val DATASTORE = "my_datastore"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE)


class DataStoreManager(val context: Context) {


    /* suspend fun storeValue(value: String, key:  Preferences.Key<String>) {
        context.dataStore.edit {
            it[key] = value
        }
        Log.d("DATASTORE", "Value saved: $value")

    }*/

    suspend fun <T> storeValue(value: T, key: Preferences.Key<T>) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
        Log.d("DATASTORE", "Value saved: $value")
    }


    suspend fun deleteValue(key: Preferences.Key<*>) {
        context.dataStore.edit { preferences ->
            preferences.remove(key)
        }
        Log.d("DATASTORE", "Key deleted: ${key.name}")
    }

    suspend fun hasKeyWithValue(key:  Preferences.Key<String>): Boolean {

        return withContext(Dispatchers.IO) {
            context.dataStore.data.map { preferences ->
                preferences[key] ?: ""
            }.first().isNotEmpty()
        }
    }



    // val token: String = getValueFromDataStore(TOKEN, "")
    // val someValue: Int = getValueFromDataStore(SOME_KEY, 0)
    suspend inline fun <reified T : Any> getValueFromDataStore(key: Preferences.Key<T>, defaultValue: T): T {
        return withContext(Dispatchers.IO) {
            context.dataStore.data.map { preferences ->
                preferences[key] ?: defaultValue
            }.first()
        }
    }


}