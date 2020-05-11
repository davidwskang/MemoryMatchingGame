package com.davidwskang.memorymatchinggame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
}
