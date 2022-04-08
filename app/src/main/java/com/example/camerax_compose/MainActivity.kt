package com.example.camerax_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.example.camerax_compose.ui.screens.camera_screen.CameraViewModel
import com.example.camerax_compose.ui.screens.screenshot_screen.ScreenShotScreen
import com.example.camerax_compose.ui.screens.splash_screen.SplashScreen
import com.example.camerax_compose.ui.screens.video_screen.VideoScreen
import java.io.File

var file: File? = null

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
                    composable(CAMERA_SCREEN.name){
                        val cameraViewModel: CameraViewModel = hiltViewModel()
                        CameraScreen(mainNav = mainNav, vm = cameraViewModel)
                    }
                    composable(VIDEO_SCREEN.name){
                        VideoScreen(file = file!!) {
                            val prevDest = mainNav.previousBackStackEntry?.destination?.route?:CAMERA_SCREEN.name
                            mainNav.navigate(prevDest)
                        }
                    }
                }
            }
        }
    }
}




