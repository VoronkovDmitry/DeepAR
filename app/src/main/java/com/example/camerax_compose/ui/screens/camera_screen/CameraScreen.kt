package com.example.camerax_compose.ui.screens.camera_screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.camerax_compose.ui.screens.camera_screen.ui_components.DeepARCamera


@Composable
fun CameraScreen(
    vm: CameraViewModel,
    mainNav:NavController
) {

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

    val deepAREventObserver = LifecycleEventObserver{ source: LifecycleOwner, event: Lifecycle.Event ->
        when(event){
            Lifecycle.Event.ON_START -> {vm.checkPermission(requestPermission, lifecycleOwner)}
            Lifecycle.Event.ON_STOP -> {vm.stopDeepAR()}

            Lifecycle.Event.ON_RESUME -> {vm.checkPermission(requestPermission, lifecycleOwner)}
            Lifecycle.Event.ON_PAUSE -> {}

            Lifecycle.Event.ON_CREATE -> {}
            Lifecycle.Event.ON_DESTROY -> {vm.stopDeepAR()}


            else -> {}
        }
    }

    DisposableEffect(key1 = lifecycleOwner, effect = {
        lifecycleOwner.lifecycle.addObserver(deepAREventObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(deepAREventObserver)
        }
    })
    DeepARCamera(vm, mainNav = mainNav)

}