package com.example.hangi_il_ilce.services

import com.example.hangi_il_ilce.models.ZipCodeResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {
    @POST("{il}")
     fun findByCityKey(@Path("il") cityKey:String): Call<ZipCodeResponse>
}