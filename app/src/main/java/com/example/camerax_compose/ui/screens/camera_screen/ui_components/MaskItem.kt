package com.example.camerax_compose.ui.screens.camera_screen.ui_components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.camerax_compose.ui.theme.Teal200

@Composable
fun MaskItem(
    onClick:()->Unit,
    selected:Boolean,
    maskName:String
) {
    val configuration = LocalConfiguration.current
    val width = configuration.screenWidthDp
    val borderColor = remember(selected) {
        if (selected)
            Teal200
        else
            Color.Gray
    }
    Box(modifier = Modifier
        .padding(5.dp)
        .size((width / 4).dp)
        .clip(RoundedCornerShape(10))
        .border(2.dp, borderColor, RoundedCornerShape(10))
        .clickable {
            onClick()
        }){
        Text(
            text = maskName,
            color = borderColor,
            modifier = Modifier
                .padding(2.dp)
                .align(Alignment.BottomCenter)
        )
    }
}