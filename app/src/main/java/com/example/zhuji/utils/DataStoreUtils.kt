package com.example.zhuji.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.clear
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.io.IOException

object DataStoreUtils {
    private lateinit var dataStore: DataStore<Preferences>
    private const val preferenceName = "ComposeDataStore"

    fun init(context: Context){
        dataStore = context.createDataStore(preferenceName)
    }

    @Suppress("UNCHECKED_CAST")
    fun <U> getSyncData(key: String, default: U): U {
        val res = when (default) {
            is String -> readStringData(key, default)
            else -> throw IllegalArgumentException("error")
        }
        return res as U
    }

    @Suppress("UNCHECKED_CAST")
    fun <U> getData(key: String, default: U): Flow<U> {
        val data =  when (default) {
            is String -> readStringFlow(key, default)
            else -> throw IllegalArgumentException("error")
        }
        return data as Flow<U>
    }

    suspend fun <U> putData(key: String, value: U) {
        when (value) {
            is String -> saveStringData(key, value)
            is Int -> saveIntData(key, value)
            else -> throw IllegalArgumentException("error")
        }
    }

    fun <U> putSyncData(key: String, value: U) {
        when (value) {
            is String -> saveSycnStringData(key, value)
            is Int -> saveSyncIntData(key, value)
            else -> throw IllegalArgumentException("error")
        }
    }
    //save
    suspend fun saveStringData(key:String,value: String) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[preferencesKey<String>(key)] = value
        }
    }
    fun saveSycnStringData(key: String,value: String) = runBlocking { saveStringData(key, value) }


    suspend fun saveIntData(key: String,value: Int){
        dataStore.edit { mutablePreferences ->
            mutablePreferences[preferencesKey<Int>(key)] = value
        }
    }
    fun saveSyncIntData(key: String, value: Int) = runBlocking { saveIntData(key, value) }
    //red
    fun readStringFlow(key: String, default: String = ""): Flow<String> =
        dataStore.data
            .catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }.map {
                it[preferencesKey(key)] ?: default
            }

    fun readStringData(key: String, default: String = ""): String {
        var value = ""
        runBlocking {
            dataStore.data.first {
                value = it[preferencesKey(key)] ?: default
                true
            }
        }
        return value
    }

    suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }

    fun clearSync() {
        runBlocking {
            dataStore.edit {
                it.clear()
            }
        }
    }
}