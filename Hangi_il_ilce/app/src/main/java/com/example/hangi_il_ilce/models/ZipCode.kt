package com.example.hangi_il_ilce.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ZipCode(
    @SerializedName("plaka")
    @Expose
    var cityCode:String,

    @SerializedName("il")
    @Expose
    var province:String,

    @SerializedName("ilce")
    @Expose
    var district:String,

    @SerializedName("semt_bucak_belde")
    @Expose
    var suburbAreaTownPc:String,

    @SerializedName("mahalle")
    @Expose
    var town:String,

    @SerializedName("pk")
    @Expose
    var pk:String,
)
