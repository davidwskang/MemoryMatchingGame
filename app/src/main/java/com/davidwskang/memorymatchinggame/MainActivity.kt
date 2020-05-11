package com.davidwskang.memorymatchinggame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.davidwskang.memorymatchinggame.common.Game
import com.davidwskang.memorymatchinggame.common.GameDifficulty
import com.davidwskang.memorymatchinggame.game_screen.GameFragment
import com.davidwskang.memorymatchinggame.highscores_screen.HighScoresFragment
import com.davidwskang.memorymatchinggame.home_screen.HomeFragment
import com.davidwskang.memorymatchinggame.splash_screen.SplashScreenFragment

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

    fun onSplashScreenComplete() {
        val splashScreen = supportFragmentManager
            .findFragmentByTag(SplashScreenFragment.TAG)
        splashScreen?.run {
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(0, R.anim.anim_slide_down)
                .remove(this)
                .add(
                    R.id.fragment_container,
                    HomeFragment(),
                    HomeFragment.TAG
                ).commit()
        }
    }

    fun onGameSelected(game: Game) {
        val homeScreen = supportFragmentManager
            .findFragmentByTag(HomeFragment.TAG)
        homeScreen?.run {
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(0, R.anim.anim_slide_down)
                .remove(this)
                .add(
                    R.id.fragment_container,
                    GameFragment.newInstance(game),
                    GameFragment.TAG
                ).commit()
        }
    }

    fun onHighScoresSelected() {
        val homeScreen = supportFragmentManager
            .findFragmentByTag(HomeFragment.TAG)
        homeScreen?.run {
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(0, R.anim.anim_slide_down)
                .remove(this)
                .add(
                    R.id.fragment_container,
                    HighScoresFragment(),
                    HighScoresFragment.TAG
                ).commit()
        }
    }
}
