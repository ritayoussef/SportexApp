# 📱 Sportex Application

Sportex is a robust fitness tracking application that enables users to monitor their physical fitness, set personalized goals, and engage in tailored workout programs. Whether it's muscular strength, endurance, or cardiovascular health, Sportex helps users achieve their fitness aspirations with ease.

## 🌟 Features and Scope

**1. Main Menu**

- Weekly Summary: Displays completed workouts and calories burned so far.
- Today’s Plan: Highlights the active goals and planned workouts for the day.
- Settings: Provides customization options for the app.
  
**2. Profile** 

- Displays user information such as age, name, height, weight, and more.
- Tracks completed goals and achievements.
- Offers navigation to other sections of the app.
  
**3. Planner** 

- A calendar view for scheduling workouts and goals.
- Allows users to add and view upcoming workouts.
  
**4. Goal Page**

- Displays active and upcoming goals.
Users can:
- Edit existing goals.
- Delete completed or irrelevant goals.
- Mark goals as completed.
  
**5. Workout Page**

- Displays active and upcoming workouts.
Users can:
- Edit existing workouts.
- Delete completed or irrelevant workouts.
- Mark workouts as completed.
  
**6. Workout/Goal Creation** <br> 

A page for creating workouts or goals with fields like:
- Name
- Type of workout/goal
- Deadline
- Description
- AI Assistance: Suggests workouts based on fitness objectives (e.g., weight loss, strength training).
  
**7. Login & Register Screens**

- Login Screen:
Users can log in with their username and password.
Redirects to the main menu on successful login.
Allows navigation to the register screen for new users.
- Register Screen:
Users can register by providing their details (username, password, personal info).
Stores user data using the DataStore mechanism for persistence and appears on Profile Screen.
Redirects to the main menu after successful registration.

## 🛠️ Data Persistence

Sportex uses DataStore for data persistence. The application leverages Gson for serializing and deserializing complex data types like Workout and Goal into JSON strings.

## How Data is Stored:
### Gson Library:
- Converts Workout and Goal objects into JSON strings for storage.
- Converts JSON strings back into objects when retrieving data.
### DataStore:
- Stores the serialized JSON strings for workouts, goals, and user data.
- Ensures data is retained across app restarts.
### Gson Library Integration:
- Gson is used for its simplicity in handling JSON serialization and deserialization.
- This approach avoids the overhead of database setup, making it lightweight and efficient.
  
## 💾 Key Data Operations

### Workouts

- Add: Add new workouts with relevant details.
- Edit: Modify an existing workout's details.
- Delete: Remove a workout.
- Complete: Mark a workout as completed. The status persists across app restarts.
### Goals

- Add: Add new goals with relevant details.
- Edit: Modify an existing goal's details.
- Delete: Remove a goal.
- Complete: Mark a goal as completed. The completion status persists across app restarts.
  
## 🚀 Getting Started

**1- Clone the repository:** <br>
 git clone https://github.com/YourUsername/Sportex.git <br>
**2- Open the project in Android Studio.** <br>
**3- Build and run the app on an emulator or physical device.**

## 📦 Dependencies

### Libraries Used:
 - Gson:
For serialization and deserialization of Workout and Goal objects.
Official documentation: Gson
- DataStore:
For storing and retrieving persistent key-value data.
Official documentation: DataStore

## 🤝 Contributors
Rita Youssef, Naomie Edward, Eric Stoian and Alexander Burlec-Plaies
Team Longfoot
