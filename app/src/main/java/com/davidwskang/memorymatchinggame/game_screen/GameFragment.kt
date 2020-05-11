package com.davidwskang.memorymatchinggame.game_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.davidwskang.memorymatchinggame.R
import com.davidwskang.memorymatchinggame.common.Game

class GameFragment : Fragment() {

    lateinit var game: Game

    companion object {
        const val TAG = "game"
        private const val GAME_KEY = "game"

        fun newInstance(game: Game) : GameFragment {
            val args = Bundle()
            args.putParcelable(GAME_KEY, game)
            val fragment = GameFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        game = arguments?.getParcelable(GAME_KEY)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.fragment_game_screen,
        container,
        false)
}