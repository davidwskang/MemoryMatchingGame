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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_enter_high_score.*

class EnterHighScoreFragment : Fragment() {

    companion object {
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
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        score = arguments?.getInt(SCORE_KEY)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.fragment_enter_high_score,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        your_score_text.text = "Your score: $score"
        beginArrowAnim()
        up_btn.setOnClickListener { onUpBtnPressed() }
        down_btn.setOnClickListener { onDownBtnPressed() }
        enter_btn.setOnClickListener { onEnterBtnPressed() }
        back_btn.setOnClickListener { onBackPressed() }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    private fun onUpBtnPressed() {
        nameArray[currNameIndex]--
        if (nameArray[currNameIndex] == -1) {
            nameArray[currNameIndex] = 25
        }
        updateLetter()
    }

    private fun onDownBtnPressed() {
        nameArray[currNameIndex]++
        if (nameArray[currNameIndex] == ALPHABET.length) {
            nameArray[currNameIndex] = 0
        }
        updateLetter()
    }

    private fun onEnterBtnPressed() {
        updateArrow(false, currNameIndex)
        currNameIndex++
        if (currNameIndex < 3) {
            updateArrow(true, currNameIndex)
            return
        }
        // completed selecting initials
        val highScore = HighScore(
            initials = "${ALPHABET[nameArray[0]]}${ALPHABET[nameArray[1]]}${ALPHABET[nameArray[2]]}",
            score = score
        )
        compositeDisposable.add(
            HighScoresDatabase.getInstance(context!!)
                .highScoresDao()
                .insert(highScore)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    (activity as MainActivity).onEnterHighScoreComplete()
                }
        )
    }

    private fun onBackPressed() {
        if (currNameIndex == 0) return
        updateArrow(false, currNameIndex)
        currNameIndex--
        updateArrow(true, currNameIndex)
    }

    private fun beginArrowAnim() {
        arrowAnim.duration = 1000
        arrowAnim.repeatCount = Animation.INFINITE
        name_label_container[currNameIndex].startAnimation(arrowAnim)
    }

    private fun updateArrow(enable : Boolean, index : Int) {
        if (enable) {
            name_label_container[index].startAnimation(arrowAnim)
            name_label_container[index].visibility = View.VISIBLE
        } else {
            name_label_container[index].clearAnimation()
            name_label_container[index].visibility = View.INVISIBLE
        }
    }

    private fun updateLetter() {
        when (currNameIndex) {
            0 -> first_letter
            1 -> second_letter
            else -> third_letter
        }.text = ALPHABET[nameArray[currNameIndex]].toString()
    }
}