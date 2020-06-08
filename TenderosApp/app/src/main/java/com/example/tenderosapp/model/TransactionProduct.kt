package com.example.tenderosapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class TransactionProduct() : Parcelable {
    var productId: String = ""
    var productName: String = ""
    var returnedAmount: Int = 0
    var deliveredAmount: Int = 0
    var total: Int = 0

    constructor(productId : String, productName : String, returnedAmount: Int, deliveredAmount: Int, total: Int) : this() {
        this.productId = productId
        this.productName = productName
        this.returnedAmount = returnedAmount
        this.deliveredAmount = deliveredAmount
        this.total = total
    }
}