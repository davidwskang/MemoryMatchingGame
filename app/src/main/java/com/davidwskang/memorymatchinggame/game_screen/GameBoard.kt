package com.davidwskang.memorymatchinggame.game_screen

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.GridView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.davidwskang.memorymatchinggame.R
import com.davidwskang.memorymatchinggame.common.Game
import com.davidwskang.memorymatchinggame.common.GameCard
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.viewholder_card.view.*


class GameBoard : GridView {

    companion object {
        private const val FLIP_ANIM_DUR = 600L
        private const val FOURTH = 0.25
        private const val FIFTH = 0.2
        private const val SIXTH = 0.166
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    private val white by lazy { ContextCompat.getColor(context, R.color.white) }
    private val green by lazy { ContextCompat.getColor(context, R.color.s_green) }

    var listener: GameBoardListener? = null
        set(value) {
            field = value
            numColumns = listener!!.getGameModel().cols
            val dp = Resources.getSystem().displayMetrics
            adapter = CardsAdapter(
                context!!,
                listener!!.getCards(),
                dp.getCardHeight(this.listener!!.getGameModel().rows),
                dp.getCardWidth(this.listener!!.getGameModel().cols)
            )
            setOnItemClickListener { _, _, position, _ ->
                if (!flipping) {
                    listener?.onCardSelected(position)
                }
            }

        }

    private var flipping = false

    fun flip(position: Int, faceUp: Boolean) {
        val view = get(position)
        flipping = true
        view.animate()
            .scaleX(0f)
            .setDuration(FLIP_ANIM_DUR / 2)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .withEndAction {
                updateCardView(position, faceUp)
                view.animate()
                    .scaleX(1f)
                    .setDuration(FLIP_ANIM_DUR / 2)
                    .setInterpolator(AccelerateDecelerateInterpolator())
                    .withEndAction {
                        flipping = false
                        listener?.onCardAnimationComplete()
                    }
                    .start()
            }
            .start()
    }

    private fun updateCardView(position: Int, faceUp: Boolean) {
        val view = get(position)
        if (faceUp) {
            view.name.visibility = View.INVISIBLE
            view.image.visibility = View.VISIBLE

            val card = listener?.getCards()?.get(position)
            Picasso.get()
                .load(card?.imgUrl)
                .into(view.image)

            view.card_background.setBackgroundColor(white)
        } else {
            view.image.visibility = View.INVISIBLE
            view.name.visibility = View.VISIBLE
            view.card_background.setBackgroundColor(green)
        }
    }

    private fun DisplayMetrics.getCardWidth(cols: Int): Int {
        val percent =
            if (cols == 5) FIFTH
            else SIXTH
        return (widthPixels * percent).toInt()
    }

    private fun DisplayMetrics.getCardHeight(rows: Int): Int {
        val percent =
            if (rows == 4) FOURTH
            else FIFTH
        return (widthPixels * percent).toInt()
    }

    interface GameBoardListener {
        fun getGameModel(): Game
        fun getCards(): List<GameCard>
        fun onCardSelected(position: Int)
        fun onCardAnimationComplete()
    }
}