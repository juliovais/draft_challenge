package com.example.bookstore.core

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsDataStore() {
    suspend fun setBoolean(context: Context, key: String, value: Boolean) {
        val dataStoreKey = booleanPreferencesKey(key)
        context.dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }

    suspend fun getBoolean(context: Context, key: String): Boolean {
        val dataStoreKey = booleanPreferencesKey(key)
        val settings = context.dataStore.data.first()
        return settings[dataStoreKey] ?: false
    }
}