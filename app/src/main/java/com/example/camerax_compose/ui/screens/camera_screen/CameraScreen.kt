package com.example.camerax_compose.ui.screens.camera_screen

import android.view.SurfaceView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.camerax_compose.data.ParcelablePhoto
import com.example.camerax_compose.ui.screens.MainNavGraphs
import com.example.camerax_compose.ui.screens.camera_screen.ui_components.DeepARCamera
import com.example.camerax_compose.ui.screens.screenshot_screen.ScreenShotScreen


@Composable
fun CameraScreen(
    vm: CameraViewModel,
    mainNav:NavController) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current


    val requestPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = {
            val result = vm.isPermissionsGranted()
            if (result)
                vm.setUpCamera(
                    lifecycleOwner = lifecycleOwner
                )
        }
    )

    DisposableEffect(key1 = lifecycleOwner, effect = {
        vm.checkPermission(
            requestPermission = requestPermission,
            lifecycleOwner = lifecycleOwner
        )
        onDispose {
            vm.stopDeepAR()
        }
    })
    DeepARCamera(vm, mainNav = mainNav)

}