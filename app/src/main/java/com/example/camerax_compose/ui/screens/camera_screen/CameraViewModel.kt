package com.example.camerax_compose.ui.screens.camera_screen

import ai.deepar.ar.DeepAR
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.camera.core.CameraSelector
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.example.camerax_compose.deepAR_licence_key
import com.example.camerax_compose.deepar_components.DeepARCameraSetUp
import com.example.camerax_compose.deepar_components.DeepAREventListener
import com.example.camerax_compose.deepar_components.DeepARSurfaceProvider
import com.example.camerax_compose.domain.PermissionRepository
import com.example.camerax_compose.file
import com.example.camerax_compose.list_mask
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val permissionRep:PermissionRepository,
    @ApplicationContext val context: Context
):ViewModel() {

    val isRecording = mutableStateOf(false)

    //var file:File? = null

    val currentMask = mutableStateOf("none")

    var deepAR = DeepAR(context)

    val screenShot: MutableState<String> = mutableStateOf("")

    private var deepARCameraSetUp:DeepARCameraSetUp? = null

    private var surfaceProvider:DeepARSurfaceProvider? = null

    val maskPanelVisible = mutableStateOf(true)

    private var currentCameraOrientation = CameraSelector.LENS_FACING_FRONT

    var isVideoVisible = mutableStateOf(false)

    fun switchMaskPanelVisible(){
        maskPanelVisible.value = !maskPanelVisible.value
    }

    fun setUpCamera(
        lifecycleOwner: LifecycleOwner,
    ){
        Log.d("DEEPAREVENT","setUpCamera")
        surfaceProvider = DeepARSurfaceProvider(
            deepAR = deepAR,
            context = context,
        )
        deepARCameraSetUp = DeepARCameraSetUp(
            lifecycleOwner = lifecycleOwner,
            deepAR = deepAR,
            surfaceProvider
        )
        deepARCameraSetUp?.setUpCamera(context = context)
    }

    fun initializeDeepAR(){

        Log.d("DEEPAREVENT","initializeDeepAR")
        deepAR.setLicenseKey(deepAR_licence_key)
        val deepAREventListener = DeepAREventListener(
            onScreenShotTaken = {photo: String? ->
                //_photo = photo ?:""
                screenShot.value = photo ?: ""
            },
            onVideoRecording = {

            },
            init = {
                deepAR.switchEffect("mask",getFilterPath(currentMask.value))
            }
        )
        deepAR.initialize(context,deepAREventListener)
    }

    fun clearScreenShot(){
        screenShot.value = ""
    }

    fun clearVideo(){
        isVideoVisible.value = false
    }

    fun destroyDeepAR(){
        Log.d("DEEPAREVENT","destroyDeepAR")
        surfaceProvider?.stop()
        deepAR.setAREventListener(null)
        deepAR.release()
    }

    fun stopDeepAR(){
        Log.d("DEEPAREVENT","stopDeepAR")
        try {
            val cameraProvider = deepARCameraSetUp?.cameraProviderFuture?.get()
            cameraProvider?.unbindAll()
        }catch (e:Exception){

        }
        surfaceProvider?.stop()
        deepAR.release()
    }


    fun switchDeepAREffect(slot:String,effectName:String){
        val isEffectExist =
            when(slot){
                "mask" -> list_mask.contains(effectName)
                "effect" -> false
                "" -> false
                else -> false
            }
        if (isEffectExist){
            deepAR.switchEffect(slot,getFilterPath(effectName))
            currentMask.value = effectName
        }
    }

    private fun getFilterPath(filterName: String): String? {
        return if (filterName == "none") {
            null
        }
        else
            "file:///android_asset/$filterName"
    }

    fun takeScreenShot(){
        deepAR.takeScreenshot()
    }

    fun switchCamera(){
        deepARCameraSetUp?.changeOrientation(context)
    }

    fun isPermissionsGranted():Boolean{
        return permissionRep.isPermissionGranted()
    }

    fun checkPermission(
        requestPermission: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>?,
        lifecycleOwner: LifecycleOwner
    ){
        initializeDeepAR()
        requestPermission?.let { permission->
            permissionRep.checkPermissions(
                isPermissionGranted = isPermissionsGranted(),
                lifecycleOwner = lifecycleOwner,
                requestPermission = permission,
                ifGranted = {
                    setUpCamera(lifecycleOwner)
                }
            )
            return
        }
        setUpCamera(lifecycleOwner = lifecycleOwner)
    }


    fun startVideo(width:Int,height:Int){
        if (!isRecording.value){
            file = File(context.filesDir,
                UUID.randomUUID().toString() +
                ".mp4"
            )
            deepAR.startVideoRecording(file.toString(), width, height)
            Toast.makeText(context, "Recording started.", Toast.LENGTH_SHORT).show()
            isRecording.value = true
        }
    }

    fun stopVideo(){
        if (isRecording.value){
            deepAR.stopVideoRecording()
            stopDeepAR()
            Toast.makeText(
                context,
                "Recording " + file?.name + " saved.",
                Toast.LENGTH_LONG
            ).show()
            isRecording.value = false
            isVideoVisible.value = true
        }
    }



}