package com.example.sportsappteamlongfoot.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sportsappteamlongfoot.data.Goal
import com.example.sportsappteamlongfoot.ui.BottomBar
import com.example.sportsappteamlongfoot.ui.MyViewModelSimpleSaved
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import java.time.LocalDate


//For debugging purposes
private val goals: List<Goal> = listOf(
    Goal("Run 25 km","Preparing for a competition","December 19 2029"),
    Goal("Bike 50km ","Just for fun","March 2 2021"),
    Goal("Do 60 Push-Ups","To be stronger","June 7 2027")
)



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GoalDetailsScreen(
    navController: NavController,
    viewModel: MyViewModelSimpleSaved
) {
    val goalsState = viewModel.goals.collectAsState()
    val activeGoals = goalsState.value.filter {
        LocalDate.parse(it.date) == LocalDate.now()
    }
    val upcomingGoals = goalsState.value.filter {
        LocalDate.parse(it.date).isAfter(LocalDate.now())
    }

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
                    text = "Goals",
                    style = MaterialTheme.typography.headlineLarge,
                    fontSize = 32.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Active Goals Section
            Text(
                text = "Active Goal",
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 24.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (activeGoals.isNotEmpty()) {
                GoalCard(
                    goal = activeGoals.first(),
                    showButtons = true,
                    onEditClick = { updatedGoal -> viewModel.editGoal(updatedGoal) },
                    onDeleteClick = { viewModel.deleteGoal(activeGoals.first()) },
                    onCompleteClick = { viewModel.completeGoal(activeGoals.first()) }
                )
            } else {
                Text(
                    text = "No active goals for today!",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Upcoming Goals Section
            Text(
                text = "Upcoming Goals",
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 24.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(upcomingGoals) { goal ->
                    GoalCard(
                        goal = goal,
                        showButtons = true,
                        onEditClick = { updatedGoal -> viewModel.editGoal(updatedGoal) },
                        onDeleteClick = { viewModel.deleteGoal(goal) },
                        onCompleteClick = { viewModel.completeGoal(goal) }
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
fun GoalCard(
    goal: Goal,
    showButtons: Boolean,
    onDeleteClick: () -> Unit,
    onCompleteClick: () -> Unit,
    onEditClick: (Goal) -> Unit
) {
    val isCompleted = remember { mutableStateOf(goal.description.contains("(Completed)")) } // Track completion status
    val showEditDialog = remember { mutableStateOf(false) }

    if (showEditDialog.value) {
        EditGoalDialog(
            goal = goal,
            onDismiss = { showEditDialog.value = false },
            onSave = { updatedGoal ->
                onEditClick(updatedGoal)
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
                text = "Goal Name: ${goal.name}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Description: ${goal.description}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Target Date: ${goal.date}",
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
fun EditGoalDialog(
    goal: Goal,
    onDismiss: () -> Unit,
    onSave: (Goal) -> Unit
) {
    val updatedDescription = remember { mutableStateOf(goal.description) }
    val updatedDate = remember { mutableStateOf(goal.date) }

    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Goal") },
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
                onSave(goal.copy(
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
