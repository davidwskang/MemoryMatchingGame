package com.davidwskang.memorymatchinggame.highscores_screen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davidwskang.memorymatchinggame.R
import kotlinx.android.synthetic.main.viewholder_highscore.view.*

class HighScoresAdapter(private val context: Context?) :
    RecyclerView.Adapter<HighScoresViewHolder>() {

    val highScores = ArrayList<HighScore>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighScoresViewHolder {
        val view = LayoutInflater
            .from(context)
            .inflate(
                R.layout.viewholder_highscore,
                parent,
                false
            )
        return HighScoresViewHolder(view)
    }

    override fun getItemCount(): Int = highScores.size

    override fun onBindViewHolder(holder: HighScoresViewHolder, position: Int) {
        holder.bind(highScores[position], position)
    }

}

class HighScoresViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(score: HighScore, position: Int) {
        itemView.initial_text.text = score.initials
        itemView.score_text.text = score.score.toString()
        itemView.rank_text.text = "${position + 1}."
    }
}