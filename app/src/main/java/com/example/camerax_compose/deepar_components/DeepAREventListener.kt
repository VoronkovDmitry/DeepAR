package com.example.camerax_compose.deepar_components

import ai.deepar.ar.ARErrorType
import ai.deepar.ar.AREventListener
import ai.deepar.ar.DeepAR
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.util.Base64
import com.example.camerax_compose.domain.encodeToString
import java.io.ByteArrayOutputStream
import java.util.*

class DeepAREventListener(
    private val onScreenShotTaken:(photo:String?) -> Unit,
    private val onVideoRecording:() -> Unit,
    private val init:() -> Unit
):AREventListener {

    override fun screenshotTaken(bitmap: Bitmap?) {
        bitmap?.let { photo ->
            val bitmapString = photo.encodeToString()
            onScreenShotTaken(bitmapString)
        }
    }

    override fun videoRecordingStarted() {

    }

    override fun videoRecordingFinished() {

    }

    override fun videoRecordingFailed() {

    }

    override fun videoRecordingPrepared() {

    }

    override fun shutdownFinished() {

    }

    override fun initialized() {
        init()
    }

    override fun faceVisibilityChanged(p0: Boolean) {

    }

    override fun imageVisibilityChanged(p0: String?, p1: Boolean) {

    }

    override fun frameAvailable(p0: Image?) {

    }

    override fun error(p0: ARErrorType?, p1: String?) {

    }

    override fun effectSwitched(p0: String?) {

    }
    fun getFilterPath(filterName: String): String? {
        return if (filterName == "none") {
            null
        }
        else
            "file:///android_asset/$filterName"
    }

}