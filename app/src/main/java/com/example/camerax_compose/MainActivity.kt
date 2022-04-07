package com.example.camerax_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.camerax_compose.data.ParcelablePhoto
import com.example.camerax_compose.domain.toBitmap
import com.example.camerax_compose.ui.theme.CameraX_composeTheme
import dagger.hilt.android.AndroidEntryPoint
import com.example.camerax_compose.ui.screens.MainNavGraphs.*
import com.example.camerax_compose.ui.screens.camera_screen.CameraScreen
import com.example.camerax_compose.ui.screens.screenshot_screen.ScreenShotScreen
import com.example.camerax_compose.ui.screens.splash_screen.SplashScreen

var _photo = ""

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CameraX_composeTheme {
                // A surface container using the 'background' color from the theme
                val mainNav = rememberNavController()

                NavHost(navController = mainNav, startDestination = CAMERA_SCREEN.name){
                    composable(SPLASH_SCREEN.name){
                        SplashScreen()
                    }
                    composable(CAMERA_SCREEN.name,
                        arguments = listOf(
                            navArgument("screenShot", builder = {
                                nullable = true
                                type = NavType.ParcelableType<ParcelablePhoto>(ParcelablePhoto::class.java)
                                defaultValue = null
                            })
                        )){
                        CameraScreen(mainNav = mainNav)
                    }
                    composable(SCREENSHOT_SCREEN.name){
                            val photo = _photo.toBitmap()
                            ScreenShotScreen(screenShot = photo, mainNav = mainNav){}
                    }
                }
            }
        }
    }
}




