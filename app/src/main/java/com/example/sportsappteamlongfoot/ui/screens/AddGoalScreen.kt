package com.example.sportsappteamlongfoot.ui.screens

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sportsappteamlongfoot.data.Goal
import com.example.sportsappteamlongfoot.data.Workout
import com.example.sportsappteamlongfoot.ui.MyViewModelSimpleSaved
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GoalScreen(navController: NavController, viewModel: MyViewModelSimpleSaved) {
    var name by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var isSnackbarVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Screen Header
        Text(
            text = "Adding Goal",
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

        // Date Input
        OutlinedButton(
            onClick = {
                showDatePicker(context) { selectedDate ->
                    date = selectedDate
                }
            },
            modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
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
            Button(onClick = {
                val goal = Goal(name=name,date=date,description=description,isCompleted = false)
                viewModel.addGoal(goal) // Save goal to DataStore
                isSnackbarVisible = true
            },shape = RoundedCornerShape(8.dp)) {
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
                Text("Goal added successfully!")
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

