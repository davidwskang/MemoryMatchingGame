package com.davidwskang.memorymatchinggame.highscores_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.davidwskang.memorymatchinggame.R

class HighScoresFragment : Fragment() {

    companion object {
        const val TAG = "high_scores"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.fragment_highscores_screen,
        container,
        false)
}