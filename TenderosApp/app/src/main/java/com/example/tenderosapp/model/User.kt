package com.example.tenderosapp.model

class User {
    var userId: String = ""
    var userName: String = ""
    var userMail: String = ""
    var userPassword: String = ""

    constructor(userId:  String, userName: String, userMail: String, userPassword: String){
        this.userId = userId
        this.userName = userName
        this.userMail = userMail
        this.userPassword = userPassword
    }
}