package com.multicategory.uniquequiz.data.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class CategoryPreferencesRepository @Inject constructor(private val userPreferencesDataStore: DataStore<Preferences>) {

    suspend fun saveScore(
        score: String,
        key:String
    ) {
        val categoryKey = stringPreferencesKey(key)
        Result.runCatching {
            userPreferencesDataStore.edit { preferences ->
                preferences[categoryKey] = score
            }
            Log.e("TAG", "saveScore: $score category $key ", )
        }
    }

    suspend fun getScore(key:String): Result<String?> {
        return Result.runCatching {
            val categoryKey = stringPreferencesKey(key)
            val flow = userPreferencesDataStore.data
                .catch { exception ->
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { prefs ->
                    prefs[categoryKey]
                }
            val value = flow.firstOrNull()
            value
        }
    }

    companion object {
      //  val KEY_SCORE = stringPreferencesKey("score")
    }
}