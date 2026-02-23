package edu.ucne.apigoku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.apigoku.presentation.navegation.DragonBallNavHost
import edu.ucne.apigoku.ui.theme.ApigokuTheme // Asegúrate de que coincida con el nombre de tu Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApigokuTheme {
                val navController = rememberNavController()
                DragonBallNavHost(navHostController = navController)
            }
        }
    }
}