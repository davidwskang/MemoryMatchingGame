package com.davidwskang.memorymatchinggame.home_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.davidwskang.memorymatchinggame.MainActivity
import com.davidwskang.memorymatchinggame.R
import com.davidwskang.memorymatchinggame.common.Game
import com.davidwskang.memorymatchinggame.common.GameDifficulty
import kotlinx.android.synthetic.main.fragment_home_screen.*

class HomeFragment: Fragment() {

    companion object {
        const val TAG = "home"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.fragment_home_screen,
        container,
        false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.run {
            window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        }
        easy_mode_btn.setOnClickListener { onGameDifficultySelected(GameDifficulty.EASY) }
        medium_mode_btn.setOnClickListener { onGameDifficultySelected(GameDifficulty.MEDIUM) }
        hard_mode_btn.setOnClickListener { onGameDifficultySelected(GameDifficulty.HARD) }
        high_scores_btn.setOnClickListener { onHighScoresSelected() }
    }

    private fun onGameDifficultySelected(difficulty: GameDifficulty) =
        (activity as MainActivity).onGameSelected(Game(difficulty))


    private fun onHighScoresSelected() = (activity as MainActivity).onHighScoresSelected()

}