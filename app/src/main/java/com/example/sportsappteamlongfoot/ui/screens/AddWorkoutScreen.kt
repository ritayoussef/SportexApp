package com.example.sportsappteamlongfoot.ui.screens
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.sportsappteamlongfoot.data.Workout
import com.example.sportsappteamlongfoot.ui.MyViewModelSimpleSaved
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkoutScreen(navController: NavController, viewModel: MyViewModelSimpleSaved) {
    var calories by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var isSnackbarVisible by remember { mutableStateOf(false) }
    val workoutTypes = listOf(
        "Cardio", "Strength", "Flexibility", "Swimming", "Running", "Cycling", "Yoga",
        "Pilates", "Hiking", "Rowing", "Dancing", "Boxing", "CrossFit", "Zumba",
        "Climbing", "HIIT", "Martial Arts", "Weightlifting", "Stretching", "Jump Rope",
        "Walking", "Elliptical Training", "Spin Class"
    )

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Screen Header
        Text(
            text = "Adding Workout",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Name Input
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = calories,
            onValueChange = { calories = it },
            label = { Text("Calories to be burnt (estimate)") },
            modifier = Modifier.fillMaxWidth()

        )
        // Type Dropdown
        Text(text = "Type", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        DropdownMenuComponent(
            options = workoutTypes,
            selectedOption = selectedType,
            onOptionSelected = { selectedType = it }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Date Input
        OutlinedButton(
            onClick = {
                showDatePicker(context) { selectedDate ->
                    date = selectedDate
                }
            },shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (date.isEmpty()) "Select Date" else date,
                color = if (date.isNotEmpty()) Color.Black else MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Description Input
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            maxLines = 5
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Ask AI Button
        Button(
            onClick = {
                description = "AI-generated suggestion for $selectedType goal" // Simulate AI response
                navController.navigate("ai_chat_screen");
            },shape = RoundedCornerShape(8.dp),
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Ask AI")
        }
        Spacer(modifier = Modifier.height(24.dp))

        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { navController.popBackStack() },shape = RoundedCornerShape(8.dp)) {
                Text(text = "Cancel")
            }
            Button(shape = RoundedCornerShape(8.dp),onClick = {
                val workout = calories.toIntOrNull()?.let {
                    Workout(
                        name = name,
                        type = selectedType,
                        date = date,
                        description = description,
                        burntCalories = calories.toInt(),
                        isCompleted = false
                    )
                }
                if (workout != null) {
                    viewModel.addWorkout(workout)
                }
                isSnackbarVisible = true
            }) {

                Text(text = "Add")
            }
        }

        // Snackbar
        if (isSnackbarVisible) {
            Snackbar(
                action = {
                    TextButton(onClick = { isSnackbarVisible = false }) {
                        Text("OK")
                    }
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Workout added successfully!")
            }

            // Delayed dismissal
            LaunchedEffect(Unit) {
                kotlinx.coroutines.delay(3000)
                isSnackbarVisible = false
                navController.popBackStack()
            }
        }
    }
}


// Helper function for showing DatePickerDialog
private fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1 // Months are zero-based
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            // Format the date to "yyyy-mm-dd"
            val formattedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
            onDateSelected(formattedDate)
        },
        year,
        month - 1, // Correct zero-based month
        day
    )
    datePickerDialog.show()
}

@Composable
fun DropdownMenuComponent(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart)
    ) {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (selectedOption.isEmpty()) "Select" else selectedOption,
                color = if (selectedOption.isNotEmpty()) Color.Black else MaterialTheme.colorScheme.onBackground
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                    text = { Text(text = option, color = Color.Black) }
                )
            }
        }
    }
}

