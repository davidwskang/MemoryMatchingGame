package com.davidwskang.memorymatchinggame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.davidwskang.memorymatchinggame.common.Game
import com.davidwskang.memorymatchinggame.common.GameCard
import com.davidwskang.memorymatchinggame.common.GameDifficulty
import com.davidwskang.memorymatchinggame.game_screen.GameFragment
import com.davidwskang.memorymatchinggame.highscores_screen.EnterHighScoreFragment
import com.davidwskang.memorymatchinggame.highscores_screen.HighScoresFragment
import com.davidwskang.memorymatchinggame.home_screen.HomeFragment
import com.davidwskang.memorymatchinggame.splash_screen.SplashScreenFragment
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .add(
                R.id.fragment_container,
                SplashScreenFragment(),
                SplashScreenFragment.TAG
            ).commit()
    }

    val cards = ArrayList<GameCard>()

    fun onSplashScreenComplete(cards: List<GameCard>) {
        this.cards.addAll(cards)
        for (gameCard: GameCard in this.cards) {
            Picasso.get()
                .load(gameCard.imgUrl)
                .fetch()
        }
        val splashScreen = supportFragmentManager
            .findFragmentByTag(SplashScreenFragment.TAG)
        splashScreen?.run {
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.anim_slide_in_right_left,
                    R.anim.anim_slide_out_right_left
                )
                .remove(this)
                .add(
                    R.id.fragment_container,
                    HomeFragment(),
                    HomeFragment.TAG
                ).commit()
        }
    }

    fun onGameSelected(gameDifficulty: GameDifficulty) {
        val homeScreen = supportFragmentManager
            .findFragmentByTag(HomeFragment.TAG)
        val game = Game.make(gameDifficulty, cards)

        homeScreen?.run {
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.anim_slide_in_right_left,
                    R.anim.anim_slide_out_right_left
                )
                .remove(this)
                .add(
                    R.id.fragment_container,
                    GameFragment.newInstance(game),
                    GameFragment.TAG
                ).commit()
        }
    }

    fun onHighScoresSelectedFromMenu() {
        val homeScreen = supportFragmentManager
            .findFragmentByTag(HomeFragment.TAG)
        homeScreen?.run {
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.anim_slide_in_right_left,
                    R.anim.anim_slide_out_right_left
                )
                .remove(this)
                .add(
                    R.id.fragment_container,
                    HighScoresFragment.newInstance(true),
                    HighScoresFragment.TAG
                ).commit()
        }
    }

    fun onGameComplete(score: Int) {
        val gameScreen = supportFragmentManager
            .findFragmentByTag(GameFragment.TAG)
        gameScreen?.run {
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.anim_slide_in_right_left,
                    R.anim.anim_slide_out_right_left
                )
                .remove(this)
                .add(
                    R.id.fragment_container,
                    EnterHighScoreFragment.newInstance(score),
                    EnterHighScoreFragment.TAG
                ).commit()
        }
    }

    fun onGameExit() {
        val gameScreen = supportFragmentManager
            .findFragmentByTag(GameFragment.TAG)
        gameScreen?.run {
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.anim_slide_in_left_right,
                    R.anim.anim_slide_out_left_right
                )
                .remove(this)
                .add(
                    R.id.fragment_container,
                    HomeFragment(),
                    HomeFragment.TAG
                ).commit()
        }
    }

    fun onEnterHighScoreComplete() {
        val enterScreen = supportFragmentManager
            .findFragmentByTag(EnterHighScoreFragment.TAG)
        enterScreen?.run {
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.anim_slide_in_right_left,
                    R.anim.anim_slide_out_right_left
                )
                .remove(this)
                .add(
                    R.id.fragment_container,
                    HighScoresFragment.newInstance(false),
                    HighScoresFragment.TAG
                ).commit()
        }
    }

    fun onHighScoresScreenExit(backBtnLeft: Boolean) {
        val highScoresScreen = supportFragmentManager
            .findFragmentByTag(HighScoresFragment.TAG) as HighScoresFragment?

        highScoresScreen.run {
            val enterAnim =
                if (backBtnLeft) R.anim.anim_slide_in_left_right
                else R.anim.anim_slide_in_right_left
            val exitAnim =
                if (backBtnLeft) R.anim.anim_slide_out_left_right
                else R.anim.anim_slide_out_right_left
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(enterAnim, exitAnim)
                .remove(this!!)
                .add(
                    R.id.fragment_container,
                    HomeFragment(),
                    HomeFragment.TAG
                ).commit()
        }
    }
}
