package com.davidwskang.memorymatchinggame.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

enum class GameDifficulty {
    EASY, MEDIUM, HARD
}

@Parcelize
class Game private constructor(
    val rows: Int,
    val cols: Int
): Parcelable {
    companion object {
        fun make(gameDifficulty: GameDifficulty) : Game {
            return when (gameDifficulty) {
                GameDifficulty.EASY -> Game(4, 4)
                GameDifficulty.MEDIUM -> Game(4, 5)
                GameDifficulty.HARD -> Game(4, 6)
            }
        }
    }
}