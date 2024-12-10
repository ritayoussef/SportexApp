package com.example.sportsappteamlongfoot.ui

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsappteamlongfoot.data.DataStoreManager
import com.example.sportsappteamlongfoot.data.Goal
import com.example.sportsappteamlongfoot.data.Workout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.time.temporal.WeekFields.*
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class MyViewModelSimpleSaved(private val context: Context) : ViewModel() {
    // private UI state (MutableStateFlow)
    private val dataStoreManager = DataStoreManager(context)

    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String> = _firstName

    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String> = _lastName

    private val _age = MutableStateFlow("")
    val age: StateFlow<String> = _age

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _height = MutableStateFlow("")
    val height: StateFlow<String> = _height

    private val _weight = MutableStateFlow("")
    val weight: StateFlow<String> = _weight

    private val _goals = MutableStateFlow<List<Goal>>(emptyList())
    val goals: StateFlow<List<Goal>> = _goals

    private val _workouts = MutableStateFlow<List<Workout>>(emptyList())
    val workouts: StateFlow<List<Workout>> = _workouts

    /* Method called when ViewModel is first created */
    init {
        loadSettings()
        loadDataFromDataStore()
        prepopulateTestWorkouts()
        prepopulateTestGoals()
        //()
        // loadTestWorkouts()  // Ensure workouts are loaded when the ViewModel is initialized
        viewModelScope.launch {
            dataStoreManager.firstNameFlow.collect { _firstName.value = it }
            dataStoreManager.lastNameFlow.collect { _lastName.value = it }
            dataStoreManager.goalsFlow.collect { _goals.value = it }
            dataStoreManager.workoutsFlow.collect { _workouts.value = it }

        }
    }
    private fun loadDataFromDataStore() {
        viewModelScope.launch {
            dataStoreManager.workoutsFlow.collect { loadedWorkouts ->
                if (loadedWorkouts != _workouts.value) {
                    println("Loaded workouts: $loadedWorkouts")
                    _workouts.value = loadedWorkouts
                }
            }
        }
    }

    private fun prepopulateTestWorkouts() {
        viewModelScope.launch {
            val existingWorkouts = dataStoreManager.workoutsFlow.firstOrNull()
            if (existingWorkouts.isNullOrEmpty()) {
                val initialWorkouts = listOf(
                    Workout(name = "workout1", description = "test 1", date = LocalDate.now().toString(), type = "Morning Run", burntCalories = 250),
                    Workout(name = "workout2", description = "test 2", date = LocalDate.now().plusDays(2).toString(), type = "Evening Yoga", burntCalories = 250, isCompleted = true),
                    Workout(name = "workout3", description = "test 3", date = LocalDate.now().minusDays(2).toString(), type = "Evening Yoga", burntCalories = 250, isCompleted = true)
                )
                _workouts.value = initialWorkouts
                saveWorkouts(initialWorkouts)
            } else {
                println("Existing workouts detected, skipping prepopulation.")
            }
        }
    }

    private fun prepopulateTestGoals() {
        viewModelScope.launch {
            val existingGoals = dataStoreManager.goalsFlow.firstOrNull()
            if (existingGoals.isNullOrEmpty()) {
                val initialGoals = listOf(
                    Goal(name = "goal1", description = "test 1", date = LocalDate.now().toString()),
                    Goal(name = "goal2", description = "test 2", date = LocalDate.now().plusDays(2).toString())
                )
                _goals.value = initialGoals
                saveGoals(initialGoals)
            } else {
                println("Existing goals detected, skipping prepopulation.")
            }
        }
    }


    fun saveFirstName(firstName: String) {
        _firstName.value = firstName
        viewModelScope.launch {
            dataStoreManager.saveFirstName(firstName)
        }
    }

    fun saveLastName(lastName: String) {
        _lastName.value = lastName
        viewModelScope.launch {
            dataStoreManager.saveLastName(lastName)
        }
    }

    fun saveAge(age: String) {
        _age.value = age
        viewModelScope.launch {
            dataStoreManager.saveAge(age)
        }
    }

    fun saveHeight(height: String) {
        _height.value = height
        viewModelScope.launch {
            dataStoreManager.saveHeight(height)
        }
    }

    fun saveWeight(weight: String) {
        _weight.value = weight
        viewModelScope.launch {
            dataStoreManager.saveWeight(weight)
        }
    }


    private fun loadSettings() {
        viewModelScope.launch {
            dataStoreManager.usernameFlow.collect { username ->
                _username.value = username
            }
        }
        viewModelScope.launch {
            dataStoreManager.passwordFlow.collect { password ->
                _password.value = password
            }
        }
        viewModelScope.launch {
            dataStoreManager.ageFlow.collect { age ->
                _age.value = age
            }
        }
        viewModelScope.launch {
            dataStoreManager.heightFlow.collect { height ->
                _height.value = height
            }
        }
        viewModelScope.launch {
            dataStoreManager.weightFlow.collect { weight ->
                _weight.value = weight
            }
        }
        viewModelScope.launch {
            dataStoreManager.workoutsFlow.collect { savedWorkouts ->
                if (savedWorkouts.isNotEmpty()) {
                    _workouts.value = savedWorkouts
                    println("Loaded workouts: $savedWorkouts")
                }
            }
        }

        viewModelScope.launch {
            dataStoreManager.goalsFlow.collect { savedGoals ->
                if (savedGoals.isNotEmpty()) {
                    _goals.value = savedGoals
                    println("Loaded goals: $savedGoals")
                }
            }
        }
    }

    /* saves the email */
    fun saveUsername(username: String) {
        _username.value = username
        viewModelScope.launch {
            dataStoreManager.saveUsername(username)
        }
    }

    fun savePassword(password: String) {
        _password.value = password
        viewModelScope.launch {
            dataStoreManager.savePassword(password)
        }
    }

    fun checkLogin(usernameIn: String, passwordIn: String): Boolean {
        if (usernameIn == _username.value && passwordIn == _password.value) {
            return true
        }
        return false
    }

    private fun saveGoals(goals: List<Goal>) {
        viewModelScope.launch {
            dataStoreManager.saveGoals(goals)
        }
    }

    private fun saveWorkouts(workouts: List<Workout>) {
        viewModelScope.launch {
            dataStoreManager.saveWorkouts(workouts)
        }
    }

    fun addWorkout(workout: Workout) {
        val updatedWorkouts = _workouts.value.toMutableList().apply { add(workout) }
        _workouts.value = updatedWorkouts
        viewModelScope.launch {
            dataStoreManager.saveWorkouts(updatedWorkouts)
            println("Workout saved: $updatedWorkouts")
        }
    }

    fun addGoal(goal: Goal) {
        val updatedGoals = _goals.value.toMutableList().apply { add(goal) }
        _goals.value = updatedGoals
        viewModelScope.launch {
            dataStoreManager.saveGoals(updatedGoals)
            println("Goal saved: $updatedGoals")
        }
    }
    fun editGoal(updatedGoal: Goal) {
        val updatedGoals = _goals.value.map {
            if (it.name == updatedGoal.name) updatedGoal else it
        }
        _goals.value = updatedGoals
        saveGoals(updatedGoals)
    }



    fun deleteGoal(goal: Goal) {
        val updatedGoals = _goals.value.toMutableList().apply { remove(goal) }
        _goals.value = updatedGoals
        saveGoals(updatedGoals)
    }



    fun completeGoal(goal: Goal) {
        val updatedGoals = _goals.value.map {
            if (it.name == goal.name) {
                it.copy(
                    description = "${it.description} (Completed)",
                    isCompleted = true // Set the isCompleted flag to true
                )
            } else {
                it
            }
        }
        _goals.value = updatedGoals
        saveGoals(updatedGoals)
    }
    fun editWorkout(updatedWorkout: Workout) {
        val updatedWorkouts = _workouts.value.map {
            if (it.name == updatedWorkout.name) updatedWorkout else it
        }
        _workouts.value = updatedWorkouts
        saveWorkouts(updatedWorkouts)
    }

    fun deleteWorkout(workout: Workout) {
        val updatedWorkout = _workouts.value.toMutableList().apply { remove(workout) }
        _workouts.value = updatedWorkout
        saveWorkouts(updatedWorkout)
    }

    fun completeWorkout(workout: Workout) {
        val updatedWorkout = _workouts.value.map {
            if (it.name == workout.name) {
                it.copy(
                    description = "${it.description} (Completed)",
                    isCompleted = true // Set the isCompleted flag to true
                )
            } else {
                it
            }
        }
        _workouts.value = updatedWorkout
        saveWorkouts(updatedWorkout)
    }

    fun getWorkoutForToday(): Workout? {
       val today = LocalDate.now().toString() // Ensure this matches Workout.date
        println("Today's date: $today")
        return _workouts.value.firstOrNull { it.date == today }
    }

    fun getCaloriesForCurrentWeek(): Int {
        val currentWeekStart = LocalDate.now().with(of(Locale.getDefault()).dayOfWeek(), DayOfWeek.MONDAY.value.toLong())
        return _workouts.value
            .filter { it.isCompleted }
            .filter { LocalDate.parse(it.date).isAfter(currentWeekStart.minusDays(1)) && LocalDate.parse(it.date).isBefore(currentWeekStart.plusWeeks(1)) }
            .sumOf { it.burntCalories }
    }

    fun getWeekWorkoutStats(): Pair<Int, Int> {
        // Current week start
        val currentWeekStart = LocalDate.now().with(of(Locale.getDefault()).dayOfWeek(), DayOfWeek.MONDAY.value.toLong())

        // Total workouts within the current week
        val totalWorkouts = _workouts.value.count {
            it.date != null && LocalDate.parse(it.date).isAfter(currentWeekStart.minusDays(1)) &&
                    LocalDate.parse(it.date).isBefore(currentWeekStart.plusWeeks(1))
        }

        // Completed workouts within the current week
        val completedWorkouts = _workouts.value.count {
            it.date != null && LocalDate.parse(it.date).isAfter(currentWeekStart.minusDays(1)) &&
                    LocalDate.parse(it.date).isBefore(currentWeekStart.plusWeeks(1)) &&
                    it.isCompleted
        }

        println("totalWorkouts: $totalWorkouts, completedWorkouts: $completedWorkouts")
        return Pair(completedWorkouts, totalWorkouts)
    }

    fun getAllWorkouts(): StateFlow<List<Workout>> = _workouts

    fun getWeeklyWorkouts(): List<Workout> {
        // Determine the start of the current week (Monday)
        val currentWeekStart = LocalDate.now().with(WeekFields.of(Locale.getDefault()).dayOfWeek(), DayOfWeek.MONDAY.value.toLong())

        // Adjust the end of the week to Sunday
        val currentWeekEnd = currentWeekStart.plusDays(6)

        // Filter the workouts to include only those in the current week from Monday to Sunday
        val weeklyWorkouts = _workouts.value.filter {
            it.date != null && LocalDate.parse(it.date).isAfter(currentWeekStart.minusDays(1)) &&
                    LocalDate.parse(it.date).isBefore(currentWeekEnd.plusDays(1))
        }
        println("weekly" + weeklyWorkouts)
        return weeklyWorkouts
    }


    fun getWeeklyGoals(): List<Goal> {
        // Determine the start of the current week (Monday)
        val currentWeekStart = LocalDate.now().with(of(Locale.getDefault()).dayOfWeek(), DayOfWeek.MONDAY.value.toLong())

        // Filter the workouts to include only those in the current week
        val weeklyGoals = _goals.value.filter {
            it.date != null && LocalDate.parse(it.date).isAfter(currentWeekStart.minusDays(1)) &&
                    LocalDate.parse(it.date).isBefore(currentWeekStart.plusWeeks(1))
        }
        println("weekly"+weeklyGoals)
        return weeklyGoals
    }
    fun getUpcomingWorkouts(): List<Workout> {
        return _workouts.value
            .filter { LocalDate.parse(it.date).isAfter(LocalDate.now().minusDays(1)) }
            .sortedBy { LocalDate.parse(it.date) }
    }
    fun getUpcomingGoals(): List<Goal> {
        return _goals.value
            .filter { LocalDate.parse(it.date).isAfter(LocalDate.now().minusDays(1)) }
            .sortedBy { LocalDate.parse(it.date) }
    }


    fun getGoalsForCurrentWeek(): List<Goal> {
        val today = LocalDate.now()
        val weekFields = of(Locale.getDefault())
        val weekStart = today.with(weekFields.dayOfWeek(), DayOfWeek.MONDAY.value.toLong())
        val weekEnd = today.with(weekFields.dayOfWeek(), DayOfWeek.SUNDAY.value.toLong())

        return _goals.value.filter {
            val goalDate = LocalDate.parse(it.date)
            !goalDate.isBefore(weekStart) && !goalDate.isAfter(weekEnd)
        }
    }
    fun getTotalGoalsCompleted(): Int {
        return _goals.value.count { it.isCompleted }
    }

    // Function to calculate total completed workouts
    fun getTotalWorkoutsCompleted(): Int {
        return _workouts.value.count { it.isCompleted }
    }

    // Function to calculate total calories burned from completed workouts
    fun getTotalCaloriesBurned(): Int {
        return _workouts.value.filter { it.isCompleted }.sumOf { it.burntCalories }
    }

    // Function to find the most workouts completed in a single week


}