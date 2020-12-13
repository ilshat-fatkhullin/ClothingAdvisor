package com.group5.clothing_advisor.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TemperatureResponseItem(
        val id: String,
        val name: String,
        val from: Long,
        val to: Long) : Parcelable