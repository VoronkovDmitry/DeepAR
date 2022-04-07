package com.example.camerax_compose.domain

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner

class PermissionRepositoryImpl(private val context: Context):PermissionRepository {

    override fun isPermissionGranted(): Boolean {
        val cameraPermCheck =
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        val audioPermCheck =
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
        val storagePermCheck =
            if (Build.VERSION.SDK_INT <= 28)
                ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            else
                true

        return cameraPermCheck && audioPermCheck && storagePermCheck
    }

    override fun checkPermissions(
        requestPermission: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>,
        lifecycleOwner: LifecycleOwner,
        isPermissionGranted:Boolean,
        ifGranted:() -> Unit
    ){
        if (isPermissionGranted())
            ifGranted()
        else
            requestPermission.launch(arrayOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.RECORD_AUDIO,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            ))
    }

}