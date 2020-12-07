package com.group5.clothing_advisor.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ClothModel(
    val name: String? = "",
    val image_url: String? = "",
    val category_id: Int? = -1
): Parcelable
