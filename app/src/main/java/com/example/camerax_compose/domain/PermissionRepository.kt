package com.example.camerax_compose.domain

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.lifecycle.LifecycleOwner

interface PermissionRepository {

    fun isPermissionGranted():Boolean

    fun checkPermissions(
        requestPermission: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>,
        lifecycleOwner: LifecycleOwner,
        isPermissionGranted:Boolean,
        ifGranted:() -> Unit
    )
}