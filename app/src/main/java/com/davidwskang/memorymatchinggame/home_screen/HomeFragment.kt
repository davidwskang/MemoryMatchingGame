package com.davidwskang.memorymatchinggame.home_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.davidwskang.memorymatchinggame.MainActivity
import com.davidwskang.memorymatchinggame.R
import com.davidwskang.memorymatchinggame.common.GameDifficulty
import kotlinx.android.synthetic.main.fragment_home_screen.*

class HomeFragment : Fragment() {

    companion object {
        const val TAG = "home"
    }

    private var arrowAnim = AlphaAnimation(0.0f, 1.0f)
    var menuIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.fragment_home_screen,
        container,
        false
    )

    var originalArrowY = -1f
    var arrowIndices = intArrayOf(0, 1, 2, 4) // easy, medium, hard, high scores

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.run { window.statusBarColor = ContextCompat.getColor(this, R.color.black) }

        arrowAnim.duration = 1000
        arrowAnim.repeatCount = Animation.INFINITE
        arrow.startAnimation(arrowAnim)

        down_btn.setOnClickListener {
            if (originalArrowY == -1f) {
                originalArrowY = arrow.y
            }
            menuIndex++
            arrow.animate()
                .y(originalArrowY + (arrow.height.toFloat() * arrowIndices[menuIndex % 4]))
                .setDuration(0)
                .start()
        }

        up_btn.setOnClickListener {
            if (originalArrowY == -1f) {
                originalArrowY = arrow.y
            }
            menuIndex--
            if (menuIndex == -1) {
                menuIndex = arrowIndices.size - 1
            }
            arrow.animate()
                .y(originalArrowY + (arrow.height.toFloat() * arrowIndices[menuIndex % 4]))
                .setDuration(0)
                .start()
        }

        enter_btn.setOnClickListener {
            when (menuIndex % 4) {
                0 -> onGameDifficultySelected(GameDifficulty.EASY)
                1 -> onGameDifficultySelected(GameDifficulty.MEDIUM)
                2 -> onGameDifficultySelected(GameDifficulty.HARD)
                3 -> onHighScoresSelected()
            }
        }
    }

    private fun onGameDifficultySelected(difficulty: GameDifficulty) {
        (activity as MainActivity).onGameModeSelected(difficulty)
    }

    private fun onHighScoresSelected() = (activity as MainActivity).onHighScoresSelectedFromMenu()

}