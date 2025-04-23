package com.knuxstuff.rescueradar

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


class RegistrationResult {
    @SerializedName("success")
    var success: Boolean? = false

    @SerializedName("error")
    var error: String? = null

    @SerializedName("reregistered")
    var reregistered: Boolean? = false

    @SerializedName("token")
    var token: String? = null

}
class AlertResult {
    @SerializedName("success")
    var success: String? = "null"

    @SerializedName("error")
    var error: String? = "null"

}

interface APIService {
//    @GET("register")
//    fun register(@Query("imei") imei: String, @Query("meid")  meid: String): Call<RegistrationResult?>
    @GET("alert")
    fun alert(@Query("token") token: String,@Query("lat")  lat: Long, @Query("lon") lon: Long, @Query("type") type: String): Call<AlertResult>

}