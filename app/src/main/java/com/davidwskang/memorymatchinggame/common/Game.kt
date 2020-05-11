package com.davidwskang.memorymatchinggame.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

enum class GameDifficulty {
    EASY, MEDIUM, HARD
}

@Parcelize
class Game private constructor(
    val rows: Int,
    val cols: Int,
    var cards: List<GameCard>? = null
) : Parcelable {
    companion object {
        fun make(gameDifficulty: GameDifficulty): Game {
            return when (gameDifficulty) {
                GameDifficulty.EASY -> Game(4, 5) // 20
                GameDifficulty.MEDIUM -> Game(4, 6) // 24
                GameDifficulty.HARD -> Game(5, 6) // 30
            }
        }
    }
}