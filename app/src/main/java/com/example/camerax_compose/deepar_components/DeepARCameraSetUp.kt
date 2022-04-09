package com.example.camerax_compose.deepar_components

import ai.deepar.ar.CameraResolutionPreset
import ai.deepar.ar.DeepAR
import ai.deepar.ar.DeepARImageFormat
import android.content.Context
import android.content.pm.ActivityInfo
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.camerax_compose.list_mask
import com.google.common.util.concurrent.ListenableFuture
import java.nio.ByteBuffer
import java.nio.ByteOrder

class DeepARCameraSetUp(
    private val lifecycleOwner: LifecycleOwner,
    private val deepAR: DeepAR,
    private val surfaceProvider: DeepARSurfaceProvider?
    ) {

    var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>? = null

    private var useExternalCameraTexture = true

    private val NUMBER_OF_BUFFERS = 2

    private var buffers: Array<ByteBuffer?> = emptyArray()

    private var currentBuffer = 0

    private var lensFacing = CameraSelector.LENS_FACING_FRONT

    fun setUpCamera(context: Context) {
        cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        val executor = ContextCompat.getMainExecutor(context)
        cameraProviderFuture?.addListener({
            try {
                val cameraProvider = cameraProviderFuture!!.get()
                bindImageAnalysis(cameraProvider = cameraProvider,context)
            }catch (e:Exception){
                Log.d("ADD_LISTENER_ERROR",e.toString())
            }
        },executor)
    }

    fun changeOrientation(context: Context){
        lensFacing = if (lensFacing == CameraSelector.LENS_FACING_FRONT){
            surfaceProvider?.setMirror(false)
            CameraSelector.LENS_FACING_BACK
        }
        else {
            surfaceProvider?.setMirror(true)
            CameraSelector.LENS_FACING_FRONT
        }
        setUpCamera(context = context)
    }

    private fun bindImageAnalysis(
        cameraProvider: ProcessCameraProvider,
        context: Context
    ) {
        val cameraResolutionPreset = CameraResolutionPreset.P1280x720

        val width = cameraResolutionPreset.height
        val height = cameraResolutionPreset.width

        val cameraResolution = android.util.Size(width,height)
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(lensFacing)
            .build()

        if (useExternalCameraTexture){
            val preview = Preview.Builder()
                .setTargetResolution(cameraResolution)
                .build()

            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(lifecycleOwner,cameraSelector,preview)

            preview.setSurfaceProvider(surfaceProvider)
        }else{
            buffers = arrayOfNulls(NUMBER_OF_BUFFERS)
            buffers.forEachIndexed {index, byteBuffer ->
                buffers[index] = ByteBuffer.allocateDirect(width * height * 3)
                buffers[index]?.order(ByteOrder.nativeOrder())
                buffers[index]?.position(0)
            }
            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(cameraResolution)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
            val executor = ContextCompat.getMainExecutor(context)
            imageAnalysis.setAnalyzer(executor,imageAnalyzer)
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(lifecycleOwner,cameraSelector,imageAnalysis)
        }
    }

    private val imageAnalyzer =
        ImageAnalysis.Analyzer { image ->
            val byteData: ByteArray
            val yBuffer = image.planes[0].buffer
            val uBuffer = image.planes[1].buffer
            val vBuffer = image.planes[2].buffer
            val ySize = yBuffer.remaining()
            val uSize = uBuffer.remaining()
            val vSize = vBuffer.remaining()
            byteData = ByteArray(ySize + uSize + vSize)

            //U and V are swapped
            yBuffer[byteData, 0, ySize]
            vBuffer[byteData, ySize, vSize]
            uBuffer[byteData, ySize + vSize, uSize]
            buffers[currentBuffer]!!.put(byteData)
            buffers[currentBuffer]!!.position(0)
            deepAR.receiveFrame(
                buffers[currentBuffer],
                image.width, image.height,
                image.imageInfo.rotationDegrees,
                lensFacing == CameraSelector.LENS_FACING_FRONT,
                DeepARImageFormat.YUV_420_888,
                image.planes[1].pixelStride
            )
            currentBuffer = (currentBuffer + 1) % NUMBER_OF_BUFFERS
            image.close()
        }
}