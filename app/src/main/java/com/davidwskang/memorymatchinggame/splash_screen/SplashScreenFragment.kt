package com.davidwskang.memorymatchinggame.splash_screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.davidwskang.memorymatchinggame.MainActivity
import com.davidwskang.memorymatchinggame.R
import com.davidwskang.memorymatchinggame.common.GameCard
import com.davidwskang.memorymatchinggame.common.Product
import com.davidwskang.memorymatchinggame.common.Products
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.TimeUnit


class SplashScreenFragment : Fragment() {

    companion object {
        const val TAG = "splash"
        const val URL =
            "https://shopicruit.myshopify.com/admin/products.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6"
    }

    var cards = ArrayList<GameCard>()
    var compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.fragment_splash_screen,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.run { window.statusBarColor = ContextCompat.getColor(this, R.color.s_green) }

        compositeDisposable.addAll(
            Observable.interval(0L, 1L, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it > 2L && cards.isNotEmpty()) {
                        (activity as MainActivity).onSplashScreenComplete(cards)
                    } else if (it > 10L) {
                        throw Exception("Timeout error on requesting products at endpoint")
                    }
                }, {
                    Log.e(TAG, it.toString())
                }),
            getCards()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    cards.addAll(it)
                }, {
                    Log.e(TAG, it.toString())
                })
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    private fun getCards(): Single<List<GameCard>> {
        return Single.create {
            val client = OkHttpClient() // only use this once
            val request = Request.Builder().url(URL).build()
            try {
                val response = client
                    .newCall(request)
                    .execute()
                val products = Gson() // only use this once
                    .fromJson(
                        response.body?.charStream(),
                        Products::class.java
                    )

                val cards = ArrayList<GameCard>()
                for (product: Product in products.products) {
                    if (!product.title.isBlank() && !product.image.src.isBlank()) {
                        val card = GameCard(
                            product.title,
                            product.image.src
                        )
                        cards.add(card)
                    }
                    if (cards.size >= 25) break
                }
                if (cards.size < 25) {
                    it.onError(IOException("Not enough valid images at endpoint"))
                } else {
                    it.onSuccess(cards)
                }
            } catch (exception: Exception) {
                it.onError(exception)
            }
        }
    }
}