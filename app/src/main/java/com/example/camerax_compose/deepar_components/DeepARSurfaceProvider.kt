package com.example.camerax_compose.deepar_components

import ai.deepar.ar.DeepAR
import android.content.Context
import android.graphics.SurfaceTexture
import android.opengl.EGL14
import android.util.Log
import android.view.Surface
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.core.SurfaceRequest.TransformationInfo
import androidx.core.content.ContextCompat
import java.util.*

class DeepARSurfaceProvider(
    private val deepAR: DeepAR,
    private val context: Context
    ):Preview.SurfaceProvider {

    val tag = "deepARProvider_tag"

    private var isNotifyDeepar = true
    private var stop = false
    private var mirror = true
    private var orientation = 0

    private var surfaceTexture: SurfaceTexture? = null
    private var surface: Surface? = null
    private var nativeGLTextureHandle = 0


    private fun printEglState() {
        Log.d(tag, "display: " + EGL14.eglGetCurrentDisplay().nativeHandle + ", context: " + EGL14.eglGetCurrentContext().nativeHandle
        )
    }
    override fun onSurfaceRequested(request: SurfaceRequest) {
        Log.d(tag,"Surface requested")
        printEglState()

        if (nativeGLTextureHandle == 0){
            nativeGLTextureHandle = deepAR.externalGlTexture
            Log.d(tag,"request new external GL texture")
            printEglState()
        }
        if (nativeGLTextureHandle == 0 ){
            request.willNotProvideSurface()
            return
        }

        val resolution = request.resolution
        if (surfaceTexture == null){
            surfaceTexture = SurfaceTexture(nativeGLTextureHandle)
            surfaceTexture!!.setOnFrameAvailableListener { texture: SurfaceTexture? ->
                if (stop) {
                    return@setOnFrameAvailableListener
                }
                surfaceTexture!!.updateTexImage()
                if (isNotifyDeepar) {
                    deepAR.receiveFrameExternalTexture(
                        resolution.width,
                        resolution.height,
                        orientation,
                        mirror,
                        nativeGLTextureHandle
                    )
                }
            }
        }
        surfaceTexture!!.setDefaultBufferSize(resolution.width,resolution.height)
        if (surface == null)
            surface = Surface(surfaceTexture)
        val executor = ContextCompat.getMainExecutor(context)

        // register transformation listener to listen for screen orientation changes
        request.setTransformationInfoListener(
            executor
        ) { transformationInfo: TransformationInfo ->
            orientation = transformationInfo.rotationDegrees
        }
        request.provideSurface(
            surface!!, ContextCompat.getMainExecutor(context)
        ) { result: SurfaceRequest.Result ->
            when (result.resultCode) {
                SurfaceRequest.Result.RESULT_SURFACE_USED_SUCCESSFULLY -> Log.i(
                    tag,
                    "RESULT_SURFACE_USED_SUCCESSFULLY"
                )
                SurfaceRequest.Result.RESULT_INVALID_SURFACE -> Log.i(
                   tag,
                    "RESULT_INVALID_SURFACE"
                )
                SurfaceRequest.Result.RESULT_REQUEST_CANCELLED -> Log.i(
                    tag,
                    "RESULT_REQUEST_CANCELLED"
                )
                SurfaceRequest.Result.RESULT_SURFACE_ALREADY_PROVIDED -> Log.i(
                    tag,
                    "RESULT_SURFACE_ALREADY_PROVIDED"
                )
                SurfaceRequest.Result.RESULT_WILL_NOT_PROVIDE_SURFACE -> Log.i(
                    tag,
                    "RESULT_WILL_NOT_PROVIDE_SURFACE"
                )
            }
        }
    }
    fun setMirror(mirror: Boolean) {
        this.mirror = mirror
        if (surfaceTexture == null || surface == null) {
            return
        }
        isNotifyDeepar = false
        Timer().schedule(object : TimerTask() {
            override fun run() {
                isNotifyDeepar = true
            }
        }, 1000)
    }

    fun stop() {
        stop = true
    }


}