package com.example.sportsappteamlongfoot.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF75AFF5),
    secondary = Color(0xFF75AFF5),
    tertiary = Color(0xFF8CBFF6),
    background = Color(0xFFB5D0FA),
    surface = Color(0xFFA1C5F8),
    onPrimary = Color(0xFF1A1E25),
    onSecondary = Color(0xFFF5F5F5),
    onBackground =Color(0xFF1A1E25),
    onSurface =Color(0xFF1A1E25),
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF3699F1),
    secondary = Color(0xFF75AFF5),
    tertiary = Color(0xFF8CBFF6),
    background = Color(0xFFB5D0FA),
    surface = Color(0xFFA1C5F8),
//    onPrimary = Color.Black,
//    onSecondary = Color.Black,
//    onBackground = Color.Black,
//    onSurface = Color.Black
)

private val AIColorScheme = lightColorScheme(
    primary = Color(0xFF390099),
    secondary = Color(0xFF9E0059),
    tertiary = Color(0xFFFF0054),
    background = Color(0xFFFF5400),
    surface = Color(0xFFFFBD00)
)

@Composable
fun SportsAppTeamLongFootTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> LightColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

