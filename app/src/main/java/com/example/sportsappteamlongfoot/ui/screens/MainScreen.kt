package com.example.sportsappteamlongfoot.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.ui.graphics.painter.Painter

import androidx.navigation.NavController


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    onProfileClick: () -> Unit,
    onWorkoutClick: () -> Unit,
    onGoalDetailsClick: () -> Unit,
    viewModel: MyViewModelSimpleSaved
) {

    val workouts by viewModel.workouts.collectAsState()
    val todaysWorkout = viewModel.getWorkoutForToday()
    val caloriesBurnt = viewModel.getCaloriesForCurrentWeek()
    val (workoutsCompleted, totalWorkouts) = viewModel.getWeekWorkoutStats()
    val weeklyGoals = viewModel.getGoalsForCurrentWeek()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Welcome, User",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile Icon",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .clickable { onProfileClick() }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Today's Plan",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Show today's workout or "Rest Day"
            if (todaysWorkout != null) {
                LargeEmptyCard(modifier = Modifier, onWorkoutClick) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(todaysWorkout.name ?: "No Name", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            } else {
                LargeEmptyCard(modifier = Modifier, onWorkoutClick) {
                    Text(
                        text = "Rest Day",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Week Summary",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Pass dynamic values here for CaloriesCard
                CaloriesCard(title = "Burnt Calories", value = "$caloriesBurnt kcal", modifier = Modifier.weight(1f).heightIn(min = 168.dp))

                // Pass dynamic values here for WorkoutsCard
                WorkoutsCard(completed = workoutsCompleted.toString(), total = totalWorkouts, modifier = Modifier.weight(1f).heightIn(min = 120.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Goals for the Week",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if(weeklyGoals!=null){
                    weeklyGoals.forEach { goal ->
                        LargeEmptyCardGoal(modifier = Modifier, onGoalDetailsClick) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(goal.name ?: "No Name", style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    }
                } else {
                    LargeEmptyCardGoal(modifier = Modifier, onWorkoutClick) {
                        Text(
                            text = "No goals for the week!",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }


            }
        }

        BottomBar(navController = navController, modifier = Modifier.align(Alignment.BottomCenter))
    }
}


@Composable
fun CaloriesCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(horizontal = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun WorkoutsCard(completed: String, total: Int, modifier: Modifier = Modifier) {
    val progress = if (total > 0) completed.toFloat() / total else 0f

    Card(
        modifier = modifier
            .padding(horizontal = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Workouts Done",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressBar(progress = progress, completed,total) // Use dynamic progress
        }
    }
}

@Composable
fun CircularProgressBar(
    progress: Float,
    completed: String,
    total: Int,
    size: Int = 100,
    strokeWidth: Float = 8f,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(size.dp)
    ) {
        Canvas(modifier = Modifier.size(size.dp)) {
            drawCircle(
                color = Color.LightGray.copy(alpha = 0.3f), // Background circle
                style = Stroke(width = strokeWidth)
            )
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360 * progress,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }
        Text(
            text = "$completed / $total", // Display progress percentage
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun LargeEmptyCard(modifier: Modifier = Modifier, onWorkoutClick: () -> Unit, content: @Composable () -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top part for the workout name and description
                content()

                // Bottom part for the "View More" button
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Button(shape = RoundedCornerShape(8.dp),
                        onClick = { onWorkoutClick() },
                        // Making the button background transparent
                    ) {
                        Text(
                            text="View More",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun LargeEmptyCardGoal(
    modifier: Modifier = Modifier,
    onGoalClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top part for the workout name and description
                content()

                // Bottom part for the "View More" button
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Button(shape = RoundedCornerShape(8.dp),
                        onClick = onGoalClick
                    ) {
                        Text(
                            text = "View Goal Details",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun MainScreenPreview() {
//    MainScreen()
//}
