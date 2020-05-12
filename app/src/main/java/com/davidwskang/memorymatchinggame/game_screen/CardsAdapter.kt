package com.davidwskang.memorymatchinggame.game_screen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import com.davidwskang.memorymatchinggame.R
import com.davidwskang.memorymatchinggame.common.GameCard

class CardsAdapter(
    private val context: Context,
    private val cards: List<GameCard>,
    private val height: Int,
    private val width: Int
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        if (convertView == null) {
            val card = LayoutInflater
                .from(context)
                .inflate(
                    R.layout.viewholder_card,
                    parent,
                    false
                )
            card.layoutParams = AbsListView.LayoutParams(width, height)
            return card
        }
        return convertView
    }

    override fun getItem(position: Int): Any = cards[position]
    override fun getItemId(position: Int): Long = 0
    override fun getCount(): Int = cards.size
}