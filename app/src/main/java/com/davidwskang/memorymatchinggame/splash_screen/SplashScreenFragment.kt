package com.davidwskang.memorymatchinggame.splash_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.davidwskang.memorymatchinggame.MainActivity
import com.davidwskang.memorymatchinggame.R


class SplashScreenFragment : Fragment() {

    companion object {
        const val TAG = "splash"
        const val DELAY_DUR = 2000L
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_splash_screen, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.run {
            window.statusBarColor = ContextCompat.getColor(this, R.color.s_green)
        }

        view.postDelayed({
            val ma = activity as MainActivity
            ma.onSplashScreenComplete()
        }, DELAY_DUR)
    }
}