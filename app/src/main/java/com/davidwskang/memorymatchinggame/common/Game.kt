package com.davidwskang.memorymatchinggame.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

enum class GameDifficulty {
    EASY, MEDIUM, HARD
}

@Parcelize
data class Game(
    val gameDifficulty: GameDifficulty
): Parcelable