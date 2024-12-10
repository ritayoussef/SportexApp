package com.example.sportsappteamlongfoot.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.sportsappteamlongfoot.ui.navigation.AppNavHost
import com.example.sportsappteamlongfoot.ui.screens.LoginScreen
import com.example.sportsappteamlongfoot.ui.screens.RegisterScreen
import com.example.sportsappteamlongfoot.ui.theme.SportsAppTeamLongFootTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val context = LocalContext.current
            val viewModel = remember { MyViewModelSimpleSaved(context) }
            val navController = rememberNavController() // Create NavController
            AppNavHost(navController = navController, modifier = Modifier, viewModel = viewModel) // Pass NavController to AppNavHost

        }
    }
}
