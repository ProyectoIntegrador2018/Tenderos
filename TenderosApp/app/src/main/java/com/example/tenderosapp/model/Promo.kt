package com.example.tenderosapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Promo (
    var couponCode: String,
    var couponDiscount: String,
    var couponType: String,
    var description: String,
    var businessId: String,
    var businessName: String,
    var ExpiryDate: Long
) : Parcelable {
    constructor()   : this(
    couponCode =  "",
    couponDiscount = "",
    couponType =  "",
    businessId =  "",
    businessName = "",
    description = "",
    ExpiryDate =  0
    )
}
