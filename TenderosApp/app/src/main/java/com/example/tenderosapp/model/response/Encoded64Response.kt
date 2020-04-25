package com.example.tenderosapp.model.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Encoded64Response(
    @SerializedName("AccessID")
    @Expose
    var accessID: String?,

    @SerializedName("RequestUniqueID")
    @Expose
    var requestUniqueID: String?,

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
