package com.example.camerax_compose.ui.screens.camera_screen.ui_components

import android.widget.Toast
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.camerax_compose.ui.screens.camera_screen.CameraViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.camerax_compose.ui.screens.MainNavGraphs
import com.example.camerax_compose.utils.collectFlows
import kotlinx.coroutines.flow.collect


@Composable
fun TakePhotoButton(
    vm:CameraViewModel,
    modifier: Modifier,
    mainNav:NavController
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val enabled by remember {
        vm.maskPanelVisible
    }
    Button(
        onClick = {
            vm.takeScreenShot()
        },
        enabled = !enabled,
        modifier = modifier,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White
        )
        ) {}


}