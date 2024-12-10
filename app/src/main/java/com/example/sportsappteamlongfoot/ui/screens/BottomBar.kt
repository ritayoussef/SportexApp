
package com.example.sportsappteamlongfoot.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sportsappteamlongfoot.ui.navigation.Login
import com.example.sportsappteamlongfoot.ui.navigation.MainMenu
import com.example.sportsappteamlongfoot.ui.navigation.Planner
import com.example.sportsappteamlongfoot.ui.navigation.Profile

@Composable
fun BottomBar(navController: NavController, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(16.dp)) // Rounded corners
            .padding(8.dp) // Padding inside the box
            .clip(RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp), // Add some horizontal padding
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Home Icon
            IconButton(onClick = { navController.navigate(MainMenu.route) }) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = Color.White
                )
            }

            // Calendar Icon
            IconButton(onClick = { navController.navigate(Planner.route) }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Planner",
                    tint = Color.White
                )
            }

            // Profile Icon
            IconButton(onClick = { navController.navigate(Profile.route) }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    tint = Color.White
                )
            }

            // Logout Icon
            IconButton(onClick = { navController.navigate(Login.route) }) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = "Logout",
                    tint = Color.White
                )
            }
        }
    }
}



