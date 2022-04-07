package com.example.camerax_compose.ui.screens.camera_screen.ui_components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.camerax_compose.list_mask
import com.example.camerax_compose.ui.screens.camera_screen.CameraViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.camerax_compose.R
import com.example.camerax_compose.ui.theme.Teal200

@Composable
fun MaskPicker(
    modifier: Modifier,
    vm:CameraViewModel
) {

    val isVisible by remember {
        vm.maskPanelVisible
    }

    val icon = remember(isVisible) {
        if (isVisible)
            R.drawable.arrow_down
        else
            R.drawable.arrow_up
    }
    Column(
        modifier = modifier
    ) {
        IconButton(
            onClick = { vm.switchMaskPanelVisible() },
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.Gray)
                .align(CenterHorizontally)
                .size(40.dp)) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Color.White,
            )
        }
        AnimatedVisibility(
            visible = isVisible,
            enter = expandVertically(animationSpec = tween(350)),
            exit = shrinkVertically(animationSpec = tween(350))
        ) {
            MasksRow(
                vm = vm,
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topEndPercent = 10,
                            topStartPercent = 10
                        )
                    )
                    .border(
                        2.dp, Teal200,
                        RoundedCornerShape(
                            topEndPercent = 10,
                            topStartPercent = 10
                        )
                    )
                    .background(Color.LightGray)
                    .align(CenterHorizontally)
            )
        }
    }


}

