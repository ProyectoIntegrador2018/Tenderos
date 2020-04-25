package com.example.tenderosapp.model.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BalanceResponse(
    @SerializedName("ResponseCode")
    @Expose
    var responseCode: String?,

    @SerializedName("AccessID")
    @Expose
    var accessID: String?,

    @SerializedName("ResponseDescription")
    @Expose
    var responseDescription: String?,

    @SerializedName("RequestUniqueID")
    @Expose
    var requestUniqueID: String?

) : Parcelable {
    constructor()   : this(
        responseCode = "",
        accessID = "",
        responseDescription = "",
        requestUniqueID = ""
    )
}

//SAMPLE OF RESPONSE:
// {
// "ResponseCode":"103",
// "AccessID":"459511",
// "ResponseDescription":"¡Espera! Ya hiciste este movimiento y esta en proceso",
// "RequestUniqueID":"3456673745482112"
// }
