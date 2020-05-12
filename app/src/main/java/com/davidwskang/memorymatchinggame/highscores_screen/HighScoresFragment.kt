package com.davidwskang.memorymatchinggame.highscores_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.davidwskang.memorymatchinggame.MainActivity
import com.davidwskang.memorymatchinggame.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_highscores_screen.*

class HighScoresFragment : Fragment() {

    companion object {
        const val TAG = "high_scores"
        private const val BACK_BTN_PLACEMENT_KEY = "back_button_placement"

        fun newInstance(backButtonLeft : Boolean) : HighScoresFragment {
            val args = Bundle()
            args.putBoolean(BACK_BTN_PLACEMENT_KEY, backButtonLeft)
            val fragment = HighScoresFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var compositeDisposable = CompositeDisposable()
    private lateinit var adapter: HighScoresAdapter
    var backBtnPlacementLeft = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backBtnPlacementLeft = arguments?.getBoolean(BACK_BTN_PLACEMENT_KEY, true) ?: true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.fragment_highscores_screen,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.run { window.statusBarColor = ContextCompat.getColor(this, R.color.black) }

        if (backBtnPlacementLeft) {
            left_back_btn.visibility = View.VISIBLE
            right_back_btn.visibility = View.INVISIBLE
            left_back_btn.setOnClickListener { (activity as MainActivity).onHighScoresScreenExit(backBtnPlacementLeft) }
        } else {
            left_back_btn.visibility = View.INVISIBLE
            right_back_btn.visibility = View.VISIBLE
            right_back_btn.setOnClickListener { (activity as MainActivity).onHighScoresScreenExit(backBtnPlacementLeft) }
        }

        high_scores_list.layoutManager = LinearLayoutManager(context)
        adapter = HighScoresAdapter(context)
        high_scores_list.adapter = adapter

        compositeDisposable.add(
            HighScoresDatabase
                .getInstance(context!!)
                .highScoresDao()
                .getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    adapter.highScores.addAll(it)
                    adapter.notifyDataSetChanged()
                }, {
                })
        )

    }

}