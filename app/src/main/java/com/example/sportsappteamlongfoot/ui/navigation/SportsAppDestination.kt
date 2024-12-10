package com.example.sportsappteamlongfoot.ui.navigation
import android.annotation.SuppressLint
import com.example.sportsappteamlongfoot.ui.navigation.MainMenu

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.sportsappteamlongfoot.ui.MainScreen
import com.example.sportsappteamlongfoot.ui.screens.LoginScreen
import com.example.sportsappteamlongfoot.ui.screens.PlannerScreen
import com.example.sportsappteamlongfoot.ui.screens.RegisterScreen
interface AppDestination {
    val route: String
    val screen: @Composable () -> Unit
}

object MainMenu : AppDestination {
    override val route = "main_menu"
    override val screen: @Composable () -> Unit
        get() = TODO("Not yet implemented")

    @SuppressLint("NewApi")
    fun screen(onProfileClick: () -> Unit, onWorkoutClick: () -> Unit,  onGoalDetailsClick: () -> Unit): @Composable () -> Unit = {
        MainScreen(
            onProfileClick = onProfileClick,
            onWorkoutClick = onWorkoutClick,
            navController = TODO(),

            modifier = TODO(),
            viewModel = TODO(),

            onGoalDetailsClick = onGoalDetailsClick

        )
    }
}

object Register : AppDestination {
    override val route = "register"
    @SuppressLint("NewApi")
    override val screen: @Composable () -> Unit = {
        RegisterScreen(
            onNavigateToLogin = { /* Navigate to Login */ }, modifier = Modifier,
            navController = TODO(),
            viewModel = TODO()
        )
    }
}

object Login : AppDestination {
    override val route = "login"
    @SuppressLint("NewApi")
    override val screen: @Composable () -> Unit = {
        LoginScreen(onNavigateToRegister = { /* Navigate to Register */ },
            onNavigateToMenu = { /* Navigate to MainMenu */ },
            modifier = TODO(),
            viewModel = TODO()
        )
    }
}

object Planner : AppDestination {
    override val route = "planner"
    override val screen: @Composable () -> Unit = {
        PlannerScreen(
            modifier = TODO(),
            onNavigateToGoal = TODO(),
            onNavigateToWorkout = TODO(),
            viewModel = TODO(),
            navController = TODO(),
            onWorkoutClick = TODO(),
            onGoalDetailsClick = TODO()
        )
    }
}
object Profile : AppDestination {
    override val route = "profile"
    override val screen: @Composable () -> Unit = { /* Will be handled by NavHost */ }
}

val quizAppScreens = listOf(MainMenu)