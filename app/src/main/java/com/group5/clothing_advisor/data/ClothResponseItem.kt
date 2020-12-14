package com.group5.clothing_advisor.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ClothResponseItem(
    val id: String,
    val imgSrcUrl: String,
    val category: String,
    val temperature: String) : Parcelable
