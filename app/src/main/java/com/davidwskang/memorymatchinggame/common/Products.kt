package com.davidwskang.memorymatchinggame.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Products(
    val products: List<Product>
): Parcelable

@Parcelize
data class Product(
    val title : String,
    val image : ProductImage
) : Parcelable

@Parcelize
data class ProductImage(
    val src : String
) : Parcelable