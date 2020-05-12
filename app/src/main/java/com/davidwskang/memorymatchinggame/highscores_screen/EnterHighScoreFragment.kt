package com.davidwskang.memorymatchinggame.highscores_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.davidwskang.memorymatchinggame.MainActivity
import com.davidwskang.memorymatchinggame.R
import kotlinx.android.synthetic.main.fragment_enter_high_score.*

class EnterHighScoreFragment : Fragment() {

    companion object {
        const val TAG = "enter-highscore"
        private const val ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        private const val SCORE_KEY = "score"

        fun newInstance(score: Int): EnterHighScoreFragment {
            val args = Bundle()
            args.putInt(SCORE_KEY, score)
            val fragment = EnterHighScoreFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val nameArray = IntArray(3)
    private var currNameIndex = 0
    private var arrowAnim = AlphaAnimation(0.0f, 1.0f)
    private var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        score = arguments?.getInt(SCORE_KEY)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_enter_high_score, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arrowAnim.duration = 1000
        arrowAnim.repeatCount = Animation.INFINITE
        name_label_container[currNameIndex].startAnimation(arrowAnim)

        up_btn.setOnClickListener {
            nameArray[currNameIndex]--
            if (nameArray[currNameIndex] == -1) {
                nameArray[currNameIndex] = 25
            }
            updateLetter()
        }
        down_btn.setOnClickListener {
            nameArray[currNameIndex]++
            if (nameArray[currNameIndex] == 26) {
                nameArray[currNameIndex] = 0
            }
            updateLetter()
        }
        enter_btn.setOnClickListener {
            name_label_container[currNameIndex].clearAnimation()
            currNameIndex++
            if (currNameIndex == 3) {
                val initials =
                    "${ALPHABET[nameArray[0]]}${ALPHABET[nameArray[1]]}${ALPHABET[nameArray[2]]}"
                (activity as MainActivity).onEnterHighScoreComplete()
            } else {
                name_label_container[currNameIndex].startAnimation(arrowAnim)
            }
        }
    }

    private fun updateLetter() {
        val view = when (currNameIndex) {
            0 -> first_letter
            1 -> second_letter
            else -> third_letter
        }
        view.text = ALPHABET[nameArray[currNameIndex]].toString()
    }
}