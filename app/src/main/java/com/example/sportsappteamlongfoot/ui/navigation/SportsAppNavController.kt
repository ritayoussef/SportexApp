package com.example.sportsappteamlongfoot.ui.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sportsappteamlongfoot.ui.screens.LoginScreen
import com.example.sportsappteamlongfoot.ui.screens.RegisterScreen
import com.example.sportsappteamlongfoot.ui.MainScreen
import com.example.sportsappteamlongfoot.ui.MyViewModelSimpleSaved
import com.example.sportsappteamlongfoot.ui.screens.AIChatBox
import com.example.sportsappteamlongfoot.ui.screens.GoalDetailsScreen
//import com.example.sportsappteamlongfoot.ui.screens.GoalDetailsScreen
import com.example.sportsappteamlongfoot.ui.screens.GoalScreen
import com.example.sportsappteamlongfoot.ui.screens.PlannerScreen
import com.example.sportsappteamlongfoot.ui.screens.ProfileScreen
import com.example.sportsappteamlongfoot.ui.screens.WorkoutDetailsScreen
import com.example.sportsappteamlongfoot.ui.screens.WorkoutScreen
import com.example.sportsappteamlongfoot.ui.screens.WorkoutDetailsScreen
import com.example.sportsappteamlongfoot.ui.theme.SportsAppTeamLongFootTheme

// Define a CompositionLocal for accessing NavController anywhere in the composable hierarchy
val LocalNavController = compositionLocalOf<NavHostController> {
    error("No NavController found!")
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: MyViewModelSimpleSaved = viewModel()
) {
    SportsAppTeamLongFootTheme {
        NavHost(
            navController = navController,
            startDestination = Login.route,
            modifier = modifier
        ) {
            composable(route = MainMenu.route) {
                MainScreen(
                    navController = navController,
                    onProfileClick = {
                        navController.navigate(Profile.route)
                    },
                    onWorkoutClick = {
                        navController.navigate("workout_details_screen")
                    },

                    onGoalDetailsClick = { // Handle navigation to Goal Details Screen
                        navController.navigate("goal_details_screen")},


                    viewModel = viewModel
                )
            }
            composable(route = Register.route) {
                RegisterScreen(
                    onNavigateToLogin = {
                        navController.navigate(Login.route)
                    },
                    navController = navController,
                    viewModel = viewModel
                )
            }
            composable(route = Login.route) {
                LoginScreen(
                    onNavigateToRegister = {
                        navController.navigate(Register.route)
                    },
                    onNavigateToMenu = {
                        navController.navigate(MainMenu.route)
                    },
                    viewModel = viewModel
                )
            }
            composable(route = Profile.route) {
                ProfileScreen(
                    navController = navController,
                    viewModel = viewModel,
                    onNavigateToWorkout = { navController.navigate("workout_screen") },
                    onNavigateToGoal = { navController.navigate("goal_screen") }
                )
            }
            composable(route = Planner.route) {
                PlannerScreen( onWorkoutClick = {
                    navController.navigate("workout_details_screen")
                },

                    onGoalDetailsClick = { // Handle navigation to Goal Details Screen
                        navController.navigate("goal_details_screen")},viewModel = viewModel,navController = navController,onNavigateToGoal = { navController.navigate("goal_screen") },onNavigateToWorkout = { navController.navigate("workout_screen") }
                )
            }


            //Adding workout
            composable(route = "workout_screen") {
                WorkoutScreen(navController = navController, viewModel = viewModel)
            }

            //Adding goal
            composable(route = "goal_screen") {
                GoalScreen(navController = navController, viewModel = viewModel)
            }

            composable (route = "goal_details_screen"){
                GoalDetailsScreen(navController = navController, viewModel = viewModel)
            }

            composable(route = "workout_details_screen") {
                WorkoutDetailsScreen(navController = navController,  onProfileClick = {
                    navController.navigate(Profile.route)

                }, viewModel = viewModel, modifier = Modifier)
            }
            composable(route = "ai_chat_screen"){
                AIChatBox(navController = navController, viewModel = viewModel)
            }
        }
    }
}

