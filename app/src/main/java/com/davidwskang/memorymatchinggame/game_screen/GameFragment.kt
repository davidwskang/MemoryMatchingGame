package com.davidwskang.memorymatchinggame.game_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.davidwskang.memorymatchinggame.MainActivity
import com.davidwskang.memorymatchinggame.R
import com.davidwskang.memorymatchinggame.common.Game
import com.davidwskang.memorymatchinggame.common.GameCard
import kotlinx.android.synthetic.main.fragment_game_screen.*


class GameFragment : Fragment(), GameBoard.GameBoardListener {

    private lateinit var game: Game
    private val cards = ArrayList<GameCard>()
    private var flippedUpCards = ArrayList<Int>()
    private var matchedCardPositions = HashSet<Int>()
    private var turns = 0

    companion object {
        private const val GAME_KEY = "game"
        private const val CARD_ANIM_DELAY_DUR = 300L

        fun newInstance(game: Game): GameFragment {
            val args = Bundle()
            args.putParcelable(GAME_KEY, game)
            val fragment = GameFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initGameModels()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.fragment_game_screen,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ma = activity as MainActivity
        back_btn.setOnClickListener { ma.onGameExit() }
        game_board.listener = this
        game_auto_complete.setOnClickListener { ma.onGameComplete(turns) }
    }

    // GameBoardListener.getGameModel()
    override fun getGameModel(): Game = game

    // GameBoardListener.getCards()
    override fun getCards(): List<GameCard> = cards

    // GameBoardListener.onCardSelected(position:)
    override fun onCardSelected(position: Int) {
        // card already matched
        if (matchedCardPositions.contains(position)) return

        // new turn
        if (flippedUpCards.isEmpty()) {
            flippedUpCards.add(position)
            game_board.flip(position, true)
            return
        }

        val alreadyFlippedPosition = flippedUpCards[0]

        // same exact card as before
        if (alreadyFlippedPosition == position) return

        if (cards[alreadyFlippedPosition].name == cards[position].name) { // match
            matchedCardPositions.add(alreadyFlippedPosition)
            matchedCardPositions.add(position)
            // leave both flipped up.
            game_board.flip(position, true)
            flippedUpCards.clear()

        } else { // not match
            // flip new position to show user
            // flip both down on anim complete callback 'onCardAnimationComplete()'
            game_board.flip(position, true)
            flippedUpCards.add(position)
        }

        turns++
        updateTurnsCount()
    }

    // GameBoardListener.onCardAnimationComplete()
    override fun onCardAnimationComplete() {

        if (cards.size == matchedCardPositions.size) {
            game_board?.postDelayed({
                (activity as MainActivity).onGameComplete(turns)
            }, CARD_ANIM_DELAY_DUR)
        }

        if (flippedUpCards.size == 2) {
            game_board?.postDelayed({
                for (pos: Int in flippedUpCards) {
                    game_board?.flip(pos, false)
                }
                flippedUpCards.clear()
            }, CARD_ANIM_DELAY_DUR)
        }
    }

    private fun initGameModels() {
        game = arguments?.getParcelable(GAME_KEY)!!
        val numPairs = (game.cols * game.rows) / 2
        if (game.cards.size <= numPairs) return

        for (i in 0 until numPairs) {
            cards.add(game.cards[i])
            cards.add(game.cards[i])
        }
        cards.shuffle()
    }

    private fun updateTurnsCount() {
        turns_text.text = "Turns: $turns"
    }
}


