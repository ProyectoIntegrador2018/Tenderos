package com.example.tenderosapp.model.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetBalanceResponse(
    @SerializedName("AccessID")
    @Expose
    var accessID: String? = null,

    @SerializedName("RequestUniqueID")
    @Expose
    var requestUniqueID: String? = null,

    @SerializedName("Data")
    @Expose
    var data: String?
) : Parcelable {
    constructor()   : this(
        accessID = "",
        requestUniqueID = "",
        data = ""
    )
}
