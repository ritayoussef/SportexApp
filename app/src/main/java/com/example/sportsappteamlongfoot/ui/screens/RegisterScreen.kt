package com.example.sportsappteamlongfoot.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.sportsappteamlongfoot.ui.MainScreen
import com.example.sportsappteamlongfoot.ui.MyViewModelSimpleSaved
import com.example.sportsappteamlongfoot.ui.navigation.Login
import com.example.sportsappteamlongfoot.ui.navigation.MainMenu
import com.example.sportsappteamlongfoot.R

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: MyViewModelSimpleSaved
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id=R.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(100.dp) // Adjust size as needed
                .padding(bottom = 16.dp)
        )
        Text(
            text = "Register",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Username Field
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username", color = Color(0xFF1A1E25)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = Color(0xFF1A1E25)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Confirm Password Field
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password", color = Color(0xFF1A1E25)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // First Name Field
        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name", color = Color(0xFF1A1E25)) },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Last Name Field
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name", color = Color(0xFF1A1E25)) },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Age Field
        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age", color = Color(0xFF1A1E25)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Weight Field
        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Weight", color = Color(0xFF1A1E25)) },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Height Field
        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            label = { Text("Height", color = Color(0xFF1A1E25)) },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Register Button
        Button(shape = RoundedCornerShape(8.dp),onClick = {
            viewModel.saveUsername(username)
            viewModel.savePassword(password)
            viewModel.saveFirstName(firstName)
            viewModel.saveLastName(lastName)
            viewModel.saveAge(age)
            viewModel.saveWeight(weight)
            viewModel.saveHeight(height)

            navController.navigate(MainMenu.route) {
                popUpTo(Login.route) { inclusive = true }
            }
        }) {
            Text(text = "Register")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Login Navigation Button
        Button(shape = RoundedCornerShape(8.dp),onClick = onNavigateToLogin) {
            Text(text = "Already have an account? Login")
        }


    }
}
