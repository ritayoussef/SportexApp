package com.example.sportsappteamlongfoot.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val PROFILE_DATASTORE ="profile_datastore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PROFILE_DATASTORE)

class DataStoreManager (private val context: Context) {
    companion object {
        val PASSWORD = stringPreferencesKey("PASSWORD")
        val USERNAME = stringPreferencesKey("USERNAME")
        val HEIGHT = stringPreferencesKey("HEIGHT")
        val WEIGHT = stringPreferencesKey("WEIGHT")
        val GOALS = stringPreferencesKey("GOALS")
        val WORKOUTS = stringPreferencesKey("WORKOUTS")
        val FIRST_NAME = stringPreferencesKey("FIRST_NAME")
        val LAST_NAME = stringPreferencesKey("LAST_NAME")
        val AGE = stringPreferencesKey("AGE")
    }

    suspend fun saveFirstName(firstName: String) {
        context.dataStore.edit { preferences ->
            preferences[FIRST_NAME] = firstName
        }
    }

    suspend fun saveLastName(lastName: String) {
        context.dataStore.edit { preferences ->
            preferences[LAST_NAME] = lastName
        }
    }

    suspend fun saveAge(age: String) {
        context.dataStore.edit { preferences ->
            preferences[AGE] = age
        }
    }

    suspend fun savePassword(password: String) {
        context.dataStore.edit { preferences ->
            preferences[PASSWORD] = password
        }
    }

    suspend fun saveUsername(username: String) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME] = username
        }
    }

    suspend fun saveHeight(height: String) {
        context.dataStore.edit { preferences ->
            preferences[HEIGHT] = height
        }
    }

    suspend fun saveWeight(weight: String) {
        context.dataStore.edit { preferences ->
            preferences[WEIGHT] = weight
        }
    }

    suspend fun saveGoals(goals: List<Goal>) {
        val json = Gson().toJson(goals)
        println("Saving goals to DataStore: $json") // Debug log
        context.dataStore.edit { preferences ->
            preferences[GOALS] = json
        }
    }

    suspend fun saveWorkouts(workouts: List<Workout>) {
        val json = Gson().toJson(workouts)
        println("Saving workouts to DataStore: $json") // Debug log
        context.dataStore.edit { preferences ->
            preferences[WORKOUTS] = json
        }
    }

    val firstNameFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[FIRST_NAME] ?: ""
        }

    val lastNameFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[LAST_NAME] ?: ""
        }
    val ageFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[AGE] ?: ""
        }
    val passwordFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[PASSWORD] ?: ""
        }

    val usernameFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[USERNAME] ?: ""
        }

    val heightFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[HEIGHT] ?: ""
        }

    val weightFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[WEIGHT] ?: ""
        }

    // Retrieve goals as a Flow<List<String>>
    val goalsFlow: Flow<List<Goal>> = context.dataStore.data.map { preferences ->
        val json = preferences[GOALS] ?: "[]"
        Gson().fromJson(json, object : TypeToken<List<Goal>>() {}.type)
    }

    // Retrieve workouts as a Flow<List<Workout>>
    val workoutsFlow: Flow<List<Workout>> = context.dataStore.data.map { preferences ->
        val json = preferences[WORKOUTS] ?: "[]"
        println("Retrieved workouts from DataStore: $json")
        Gson().fromJson(json, object : TypeToken<List<Workout>>() {}.type)
    }
}
data class Goal(
    val name: String = "",
    val description: String = "",
    val date: String = "",
    val isCompleted: Boolean = false
)


data class Workout(
    val name: String = "",
    val type: String = "",
    val date: String = "",
    val description: String = "",
    val burntCalories: Int = 0,
    var isCompleted: Boolean = false
)
