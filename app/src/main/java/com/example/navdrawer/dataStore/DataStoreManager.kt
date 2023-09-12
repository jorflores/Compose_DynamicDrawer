package com.example.navdrawer.dataStore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


const val MERCHANT_DATASTORE = "my_datastore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = MERCHANT_DATASTORE)


class DataStoreManager(val context: Context) {

    companion object {
        val TOKEN = stringPreferencesKey("TOKEN")

    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit {
            it[TOKEN] = token
        }
        Log.d("DATASTORE","Token saved: ${token}")
    }

    suspend fun getTokenFromDataStore(): String {

        return context.dataStore.data.map { preferences ->
            preferences[TOKEN] ?: ""
        }.first()
    }


}