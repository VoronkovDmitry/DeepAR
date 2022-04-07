package com.example.camerax_compose.deepar_components

import ai.deepar.ar.DeepAR
import android.view.SurfaceHolder

class SurfaceHolderCallback(val deepAR: DeepAR):SurfaceHolder.Callback {

    override fun surfaceCreated(p0: SurfaceHolder) {

    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        deepAR.setRenderSurface(holder.surface,width, height)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        deepAR.setRenderSurface(null,0,0)
    }
}