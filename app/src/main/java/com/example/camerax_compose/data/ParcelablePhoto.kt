package com.example.camerax_compose.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParcelablePhoto(val photo:String) : Parcelable
