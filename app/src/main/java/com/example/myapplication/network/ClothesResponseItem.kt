package com.example.myapplication.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ClothesResponseItem(
    val id: String,
    @Json(name = "url") val imgSrcUrl: String) : Parcelable
