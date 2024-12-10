package com.example.sportsappteamlongfoot.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sportsappteamlongfoot.R
import com.example.sportsappteamlongfoot.data.Goal
import com.example.sportsappteamlongfoot.data.Workout
import com.example.sportsappteamlongfoot.model.AIModel
import com.example.sportsappteamlongfoot.ui.MyViewModelSimpleSaved
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

////For debugging purposes
private val sampleGoals: List<Goal> = listOf(
    Goal("Run 25 km","Preparing for a competition","December 19 2029",),
    Goal("Bike 50km ","Just for fun","March 2 2021",),
    Goal("Do 60 Push-Ups","To be stronger","June 7 2027")
)

private val sampleWorkout: List<Workout> = listOf(
    Workout("Run 25 km","Running","December 19 2029","Preparing to run a marathon"),
    Workout("Bike 50km ","Biking","March 2 2021", "Just for fun"),
    Workout("Do 60 Push-Ups","Exercises","June 7 2027", "Need to increase muscular strength")
)
@SuppressLint("NewApi", "StateFlowValueCalledInComposition")
@Composable
fun AIChatBox(
    navController: NavController,
    viewModel: MyViewModelSimpleSaved,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    var userInputInUI by rememberSaveable { mutableStateOf("") }
    var aiResponseInUI by rememberSaveable { mutableStateOf("") }
    val aiModel = AIModel()
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    var btnIsEnabled by rememberSaveable { mutableStateOf(true) }
    val focusManager = LocalFocusManager.current
    val goalsData: List<Goal> = sampleGoals
    val workoutData: List<Workout> = sampleWorkout

    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        // Title
        Text(
            text = "What can I help you with?",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Input Field
        TextField(
            value = userInputInUI,
            onValueChange = { userInputInUI = it },
            label = { Text("Type anything") },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Buttons
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Button(shape = RoundedCornerShape(8.dp),
                onClick = { navController.popBackStack() },
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            ) {
                Text("Back")
            }
            Button(shape = RoundedCornerShape(8.dp),
                onClick = {
                    aiResponseInUI = ""
                    btnIsEnabled = false
                    focusManager.clearFocus()
                    coroutineScope.launch {
                        var userInput = userInputInUI
                        var response = ""

                        if (userInputInUI.contains("workout") && userInputInUI.contains("goal")) {
                            userInput += "Here are my current workouts and goals: \n"
                            workoutData.forEach {
                                userInput += "Workout: ${it.name}, Date: ${it.date}\n"
                            }
                            goalsData.forEach {
                                userInput += "Goal: ${it.name}, Date: ${it.date}\n"
                            }

                            userInput+= "Provide the answers sports related in the following format:" +
                                    "Here are some workouts you can add: " +
                                    "Name: (suggested name)" +
                                    "Date: (suggested date)" +
                                    "Description: (suggested description)"
                        }
                        else if (userInputInUI.contains("workout")) {
                            userInput += "Here are my current workouts and goals: \n"
                            workoutData.forEach {
                                userInput += "Workout: ${it.name}, Date: ${it.date}\n"
                            }
                            goalsData.forEach {
                                userInput += "Goal: ${it.name}, Date: ${it.date}\n"
                            }

                            userInput+= "Provide the answers sports related in the following format:" +
                                    "Here are some workouts you can add: " +
                                    "Name: (suggested name)" +
                                    "Date: (suggested date)" +
                                    "Description: (suggested description)"
                        }
                        else if (userInputInUI.contains("goal")) {
                            userInput += "Here are my current workouts and goals: \n"
                            workoutData.forEach {
                                userInput += "Workout: ${it.name}, Date: ${it.date}\n"
                            }
                            goalsData.forEach {
                                userInput += "Goal: ${it.name}, Date: ${it.date}\n"
                            }

                            userInput+= "Provide the answers sports related in the following format:" +
                                    "Here are some goals you can set: " +
                                    "Name: (suggested name)" +
                                    "Date: (suggested date)" +
                                    "Description: (suggested description)"
                        }



                        response = aiModel.GenerateAIResponse(userInput).toString()

                        // Simulate typing effect
                        for (i in response.indices) {
                            aiResponseInUI = response.substring(0, i + 1)
                            delay(20)
                        }
                        btnIsEnabled = true
                    }
                },
                enabled = btnIsEnabled,
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            ) {
                Text(text = if (btnIsEnabled) "Generate" else "Typing...")
            }
        }

        // AI Response
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            SelectionContainer {
                Text(
                    text = aiResponseInUI,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        // Gemini Icon
        Image(
            painter = painterResource(R.drawable.google_gemini_icon),
            contentDescription = "Gemini",
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        )
    }
}



@SuppressLint("NewApi")
@Preview
@Composable
fun AIChatBoxPreview(){
    val mockContext = LocalContext.current
    val mockViewModel = remember {MyViewModelSimpleSaved(mockContext)}
    val mockNavController = rememberNavController()
    AIChatBox(mockNavController, mockViewModel, Modifier.fillMaxWidth())
}