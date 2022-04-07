package com.example.camerax_compose.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun collectFlows(
    lifecycleOwner: LifecycleOwner,
    onCollect:suspend CoroutineScope.() -> Unit){
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
            onCollect()
        }
    }
}