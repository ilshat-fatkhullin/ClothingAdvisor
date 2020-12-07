package com.group5.clothing_advisor.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ClothesResponseItem(
    val id: String,
    val name: String,
    val imgSrcUrl: String) : Parcelable
