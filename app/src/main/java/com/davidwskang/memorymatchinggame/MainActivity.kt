package com.davidwskang.memorymatchinggame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.davidwskang.memorymatchinggame.common.Game
import com.davidwskang.memorymatchinggame.common.GameCard
import com.davidwskang.memorymatchinggame.common.GameDifficulty
import com.davidwskang.memorymatchinggame.game_screen.GameFragment
import com.davidwskang.memorymatchinggame.highscores_screen.EnterHighScoreFragment
import com.davidwskang.memorymatchinggame.highscores_screen.HighScoresFragment
import com.davidwskang.memorymatchinggame.home_screen.HomeFragment
import com.davidwskang.memorymatchinggame.splash_screen.SplashScreenFragment

class MainActivity : AppCompatActivity() {

    lateinit var currFrag : Fragment
    val cards = ArrayList<GameCard>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        val splash = SplashScreenFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, splash)
            .commit()
        currFrag = splash
    }

    fun onSplashScreenComplete() = removeThenAddFrag(HomeFragment(), true)


    fun onGameModeSelected(gameDifficulty: GameDifficulty) {
        val game = Game.make(gameDifficulty, cards)
        removeThenAddFrag(GameFragment.newInstance(game), true)
    }

    fun onHighScoresSelectedFromMenu() {
        removeThenAddFrag(HighScoresFragment.newInstance(true), true)
    }

    fun onGameComplete(score: Int) {
        removeThenAddFrag(EnterHighScoreFragment.newInstance(score), true)
    }

    fun onGameExit() {
        removeThenAddFrag(HomeFragment(), false)
    }

    fun onEnterHighScoreComplete() {
        removeThenAddFrag(HighScoresFragment.newInstance(false), true)
    }

    fun onHighScoresScreenExit(backBtnLeft: Boolean) {
        removeThenAddFrag(HomeFragment(), !backBtnLeft)
    }

    private fun removeThenAddFrag(toFrag : Fragment, transitionRight : Boolean) {
        val transAnim = when (transitionRight) {
            true -> FragTransitionAnim(
                R.anim.anim_slide_in_right_left,
                R.anim.anim_slide_out_right_left
            )
            false -> FragTransitionAnim(
                R.anim.anim_slide_in_left_right,
                R.anim.anim_slide_out_left_right
            )
        }
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(transAnim.enterAnim, transAnim.exitAnim)
            .remove(currFrag)
            .add(R.id.fragment_container, toFrag)
            .commit()
        currFrag = toFrag
    }

    data class FragTransitionAnim(
        val enterAnim : Int,
        val exitAnim : Int
    )
}
