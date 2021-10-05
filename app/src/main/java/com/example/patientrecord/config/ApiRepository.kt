package com.example.patientrecord.config

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.example.patientrecord.data.Patient
import com.example.patientrecord.utils.UIApiCallResponseListener
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class ApiRepository  {

    private val apiInterface = ApiClient.getRetrofitInstance().create(ApiInterface::class.java)

    fun registerPatient(
        context: Context,
        patient: Patient,
        uiApiCallResponseListener: UIApiCallResponseListener
    ) {

        val response = object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.code() == 200) {
                    uiApiCallResponseListener.onSuccess("User registered successfully!")
                    return
                }

                uiApiCallResponseListener.onFailed("Failed to register user")
                return
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                uiApiCallResponseListener.onFailed( "No internet access!")
            }
        }

        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, Uri.parse(patient.photo)))
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, Uri.parse(patient.photo))
        }
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
        val image = stream.toByteArray()

        val body = HashMap<String, Any?>()
        body["patientname"] = patient.name
        body["age"] = patient.age
        body["gender"] = patient.gender
        body["mobilenumber"] = patient.mobile
        body["email"] = patient.email
        body["patientaddress"] = patient.address
        body["resultimage"] = image

        val call = apiInterface.register(body)
        call.enqueue(response)
    }

}