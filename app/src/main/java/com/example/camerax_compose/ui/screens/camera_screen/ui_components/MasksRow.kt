package com.example.camerax_compose.ui.screens.camera_screen.ui_components

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.camerax_compose.list_mask
import com.example.camerax_compose.ui.screens.camera_screen.CameraViewModel
import kotlinx.coroutines.delay

@Composable
fun MasksRow(
    modifier: Modifier,
    vm: CameraViewModel
) {

    val currentMask by remember {
        vm.currentMask
    }

    LazyRow(modifier = modifier){
        itemsIndexed(list_mask){ index, maskName ->
            MaskItem(
                onClick = {
                    vm.switchDeepAREffect(
                        slot = "mask",
                        effectName = maskName
                    )
                },
                selected = currentMask == maskName,
                maskName = maskName)
        }
    }
}