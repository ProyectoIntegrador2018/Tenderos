package com.example.tenderosapp.model

class Transaction {
    var transactionId: String = ""
    var storeId: String = ""
    var storeName: String = ""
    var businessId: String = ""
    var businessName: String = ""
    var date: Long = 0
    var saleTotal: Int = 0
    var actualBalance: Int = 0
    var previousBalance: Int = 0
    var payment: Int = 0
    var products: MutableList<TransactionProduct> = arrayListOf()

    constructor(transactionId : String, storeId: String, storeName: String, businessId: String, businessName: String, date: Long, saleTotal: Int, actualBalance: Int, previousBalance: Int, payment: Int, products: MutableList<TransactionProduct> ) {
        this.transactionId = transactionId
        this.storeId = storeId
        this.storeName = storeName
        this.businessId = businessId
        this.businessName = businessName
        this.date = date
        this.saleTotal = saleTotal
        this.actualBalance = actualBalance
        this.previousBalance = previousBalance
        this.payment = payment
        this.products = products
    }
}