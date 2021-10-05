package com.example.patientrecord.config

import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @POST("biosenseapi/api/synccamp/engagePrescription")
    fun register(
        @Body body: HashMap<String, Any?>,
    ): Call<ResponseBody>

}