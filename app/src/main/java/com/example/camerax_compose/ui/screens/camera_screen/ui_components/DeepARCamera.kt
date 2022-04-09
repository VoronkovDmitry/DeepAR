package com.example.camerax_compose.ui.screens.camera_screen.ui_components

import ai.deepar.ar.DeepAR
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.util.Log
import android.view.SurfaceView
import android.view.View
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraX
import androidx.camera.core.CameraXConfig
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.camerax_compose.data.ParcelablePhoto
import com.example.camerax_compose.deepAR_licence_key
import com.example.camerax_compose.deepar_components.DeepARCameraSetUp
import com.example.camerax_compose.deepar_components.DeepAREventListener
import com.example.camerax_compose.deepar_components.DeepARSurfaceProvider
import com.example.camerax_compose.deepar_components.SurfaceHolderCallback
import com.example.camerax_compose.domain.toBitmap
import com.example.camerax_compose.list_mask
import com.example.camerax_compose.ui.screens.MainNavGraphs
import com.example.camerax_compose.ui.screens.camera_screen.CameraViewModel
import com.example.camerax_compose.ui.screens.screenshot_screen.ScreenShotScreen
import com.example.camerax_compose.ui.screens.splash_screen.SplashScreen
import com.example.camerax_compose.ui.screens.video_screen.VideoScreen
import com.example.camerax_compose.ui.theme.Teal200
import kotlinx.coroutines.delay
import java.lang.IllegalStateException
import java.security.Permission
import java.util.jar.Manifest


@Composable
fun DeepARCamera(
    vm:CameraViewModel,
    mainNav:NavController
) {
    val context = LocalContext.current

    val surfaceView = SurfaceView(context)

    val photo by remember {
        vm.screenShot
    }


    Box(modifier = Modifier.fillMaxSize()){
        AndroidView(
            factory = {cxt ->
                surfaceView.holder.addCallback(SurfaceHolderCallback(deepAR = vm.deepAR))
                surfaceView
            })
        SwitchCameraButton(
            vm = vm,
            modifier = Modifier
                .padding(15.dp)
                .align(Alignment.TopStart)
        )

        TakePhotoButton(
            vm = vm,
            modifier = Modifier
                .padding(70.dp)
                .size(70.dp)
                .align(Alignment.BottomCenter)
            ,
            mainNav = mainNav
        )

        MaskPicker(
            vm = vm,
            modifier = Modifier
                .padding(
                    start = 15.dp,
                    end = 15.dp
                )
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )

        if (photo.isNotEmpty())
            ScreenShotScreen(screenShot = photo.toBitmap()) {
                vm.clearScreenShot()
            }

    }





}


