package com.group5.clothing_advisor.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryResponseItem(val id: String, val name: String) : Parcelable
