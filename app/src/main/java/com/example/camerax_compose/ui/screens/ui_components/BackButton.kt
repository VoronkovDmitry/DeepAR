package com.example.camerax_compose.ui.screens.ui_components

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.camerax_compose.R


@Composable
fun BackButton(
    modifier: Modifier,
    nav:NavController,
    onClick:()->Unit
) {
    IconButton(
        modifier = modifier,
        onClick = {
//        val prevDest = nav.previousBackStackEntry?.destination?.route
//        prevDest?.let {
//            nav.navigate(it)
//        }
            onClick()
    }) {
        Icon(
            painter = painterResource(id = R.drawable.arrow_back),
            contentDescription = null,
            modifier = Modifier.size(50.dp),
            tint = Color.White
        )
    }
}