package com.example.hangi_il_ilce.services

import com.example.hangi_il_ilce.models.ZipCode
import com.example.hangi_il_ilce.retrofit.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

var baseURL: String = "https://api.ubilisim.com/postakodu/il/";
class APIService {
    companion object {
        suspend fun findByCityKey(cityKey: String): List<ZipCode>? {
            return withContext(Dispatchers.IO) {
                try {
                    val response = RetrofitClient
                        .getClient(baseURL)
                        .create(ApiInterface::class.java)
                        .findByCityKey(cityKey)
                        .awaitResponse()

                    if (response.isSuccessful) {

                        response.body()?.zipCodeList
                    } else {

                        null
                    }
                } catch (e: Exception) {

                    null
                }
            }
        }
    }
}