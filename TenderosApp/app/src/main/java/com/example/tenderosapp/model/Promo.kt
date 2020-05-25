package com.example.tenderosapp.model

class Promo {
    var couponCode: String = ""
    var couponDiscount: String = ""
    var couponType: String = ""
    var description: String = ""
    var businessId: String = ""
    var businessName: String = ""
    var ExpiryDate: Long = 0

    constructor(couponCode : String, couponDiscount: String, couponType: String, description: String, businessId: String, businessName: String, ExpiryDate: Long) {
        this.couponCode = couponCode
        this.couponDiscount = couponDiscount
        this.couponType = couponType
        this.businessId = businessId
        this.businessName = businessName
        this.description = description
        this.ExpiryDate = ExpiryDate
    }
}