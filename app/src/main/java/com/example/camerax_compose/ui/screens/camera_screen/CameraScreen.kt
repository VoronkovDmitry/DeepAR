package com.example.camerax_compose.ui.screens.camera_screen

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.camerax_compose._photo
import com.example.camerax_compose.data.ParcelablePhoto
import com.example.camerax_compose.ui.screens.MainNavGraphs
import com.example.camerax_compose.ui.screens.camera_screen.ui_components.DeepARCamera
import com.example.camerax_compose.ui.screens.screenshot_screen.ScreenShotScreen


@Composable
fun CameraScreen(
    vm: CameraViewModel = hiltViewModel(),
    mainNav:NavController) {
    DeepARCamera(vm, mainNav = mainNav)



}