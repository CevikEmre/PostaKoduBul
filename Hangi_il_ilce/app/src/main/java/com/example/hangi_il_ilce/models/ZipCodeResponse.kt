package com.example.hangi_il_ilce.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ZipCodeResponse(
    @SerializedName("success")
    @Expose
    val success: Boolean,

    @SerializedName("status")
    @Expose
    val status: String,

    @SerializedName("dataupdatedate")
    @Expose
    val dataUpdateDate: String,

    @SerializedName("description")
    @Expose
    val description: String,

    @SerializedName("pagecreatedate")
    @Expose
    val pageCreateDate: String,

    @SerializedName("postakodu")
    @Expose
    val zipCodeList: MutableList<ZipCode>
)