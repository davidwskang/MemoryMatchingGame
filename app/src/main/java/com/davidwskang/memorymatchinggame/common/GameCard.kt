package com.davidwskang.memorymatchinggame.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameCard(
    val name: String,
    val imgUrl: String = ""
) : Parcelable