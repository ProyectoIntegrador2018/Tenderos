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
    var products: MutableList<TransactionProduct> = arrayListOf()
}