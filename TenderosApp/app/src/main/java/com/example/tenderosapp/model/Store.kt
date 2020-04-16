package com.example.tenderosapp.model

class Store {
    var storeId: String = ""
    var storeName: String = ""
    var businessId: String = ""
    var businessName: String = ""

    constructor(storId: String, storeName: String, businessId: String, businessName: String) {
        this.storeId = storeId
        this.storeName = storeName
        this.businessId = businessId
        this.businessName = businessName
    }
}