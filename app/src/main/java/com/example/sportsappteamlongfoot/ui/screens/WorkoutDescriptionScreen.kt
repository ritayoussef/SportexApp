package com.example.sportsappteamlongfoot.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.sportsappteamlongfoot.data.Goal
import com.example.sportsappteamlongfoot.data.Workout
import com.example.sportsappteamlongfoot.ui.BottomBar
import com.example.sportsappteamlongfoot.ui.MyViewModelSimpleSaved
import kotlinx.coroutines.flow.count
import java.time.LocalDate




@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkoutDetailsScreen(
    navController: NavController,
    onProfileClick: () -> Unit,
    viewModel: MyViewModelSimpleSaved,
    modifier: Modifier = Modifier
) {
    val workoutsState = viewModel.workouts.collectAsState()
    val activeWorkouts = workoutsState.value.filter {
        LocalDate.parse(it.date) == LocalDate.now()
    }
    val upcomingWorkouts = viewModel.getUpcomingWorkouts()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Screen Header
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Workouts",
                    style = MaterialTheme.typography.headlineLarge,
                    fontSize = 32.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Active Goals Section
            Text(
                text = "Active Workout",
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 24.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (activeWorkouts.isNotEmpty()) {
                WorkoutCard(
                    workout = activeWorkouts.first(),
                    showButtons = true,
                    onEditClick = { updatedWorkout -> viewModel.editWorkout(updatedWorkout) },
                    onDeleteClick = { viewModel.deleteWorkout(activeWorkouts.first()) },
                    onCompleteClick = { viewModel.completeWorkout(activeWorkouts.first()) }
                )
            } else {
                Text(
                    text = "No active workouts for today!",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Upcoming Goals Section
            Text(
                text = "Upcoming Workouts",
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 24.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(upcomingWorkouts) { workout ->
                    WorkoutCard(
                        workout = workout,
                        showButtons = true,
                        onEditClick = { updatedWorkout -> viewModel.editWorkout(updatedWorkout) },
                        onDeleteClick = { viewModel.deleteWorkout(workout) },
                        onCompleteClick = { viewModel.completeWorkout(workout) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        // Bottom Navigation Bar
        BottomBar(navController = navController, modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun WorkoutCard(
    workout: Workout,
    showButtons: Boolean,
    onDeleteClick: () -> Unit,
    onCompleteClick: () -> Unit,
    onEditClick: (Workout) -> Unit
) {
    val isCompleted = remember { mutableStateOf(workout.description.contains("(Completed)")) } // Track completion status
    val showEditDialog = remember { mutableStateOf(false) }

    if (showEditDialog.value) {
        EditWorkoutDialog(
            workout = workout,
            onDismiss = { showEditDialog.value = false },
            onSave = { updatedWorkout ->
                onEditClick(updatedWorkout)
                showEditDialog.value = false
            }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(BorderStroke(1.dp, Color.LightGray), shape = RoundedCornerShape(8.dp)),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Workout Name: ${workout.name}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Type: ${workout.type}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Description: ${workout.description}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                fontSize = 16.sp
            )

            if (showButtons) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(shape = RoundedCornerShape(8.dp),
                        onClick = { showEditDialog.value = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                    ) {
                        Text("Edit", color = Color.White)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(shape = RoundedCornerShape(8.dp),
                        onClick = {
                            isCompleted.value = true
                            onCompleteClick()
                        },
                        enabled = !isCompleted.value,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isCompleted.value) Color.Green else MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(if (isCompleted.value) "Completed" else "Complete")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(shape = RoundedCornerShape(8.dp),
                        onClick = { onDeleteClick() }, // Delete
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Delete", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun EditWorkoutDialog(
    workout: Workout,
    onDismiss: () -> Unit,
    onSave: (Workout) -> Unit
) {
    val updatedDescription = remember { mutableStateOf(workout.description) }
    val updatedDate = remember { mutableStateOf(workout.date) }

    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Workout") },
        text = {
            Column {
                TextField(
                    value = updatedDescription.value,
                    onValueChange = { updatedDescription.value = it },
                    label = { Text("Description") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = updatedDate.value,
                    onValueChange = { updatedDate.value = it },
                    label = { Text("Date") }
                )
            }
        },
        confirmButton = {
            Button(shape = RoundedCornerShape(8.dp),onClick = {
                onSave(workout.copy(
                    description = updatedDescription.value,
                    date = updatedDate.value
                ))
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(shape = RoundedCornerShape(8.dp),onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

