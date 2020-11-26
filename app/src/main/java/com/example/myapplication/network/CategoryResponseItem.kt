package com.example.myapplication.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryResponseItem(
    val id: Int,
    val name: String) : Parcelable