package com.ichwan.disasterlist.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException

class SetDataStore(context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "THEMES")
    private val dataStore = context.dataStore

    companion object {
        val darkModeKey = booleanPreferencesKey("DARK_MODE")

        private var instance: SetDataStore? = null

        fun getInstance(context: Context): SetDataStore {
            return instance ?: synchronized(this) {
                instance ?: SetDataStore(context).also { instance = it }
            }
        }
    }

    //setTheme working as async, because edit datastore need outside resource
    suspend fun setTheme(isDarkMode: Boolean){
        dataStore.edit { preferences -> preferences[darkModeKey] = isDarkMode }
    }

    fun getTheme(): Flow<Boolean> {
        return dataStore.data.catch { exception ->
            if (exception is IOException)
                emit(emptyPreferences())
            else
                throw exception
        }.map { preferences ->
            val uiMode = preferences[darkModeKey] ?: false
            uiMode
        }
    }
}

class SetViewModel (application: Application) : AndroidViewModel(application) {

    //ensure that when theme is change, thread work in IO thread
    private val dataStore = SetDataStore(application)
    val getTheme = dataStore.getTheme().asLiveData(Dispatchers.IO)

    fun setThemeDark(isDarkMode: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.setTheme(isDarkMode)
        }
    }
}