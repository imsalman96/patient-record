package com.example.patientrecord.utils

import androidx.annotation.MainThread

interface UIApiCallResponseListener {

    @MainThread
    fun onFailed(msg: String)

    @MainThread
    fun onSuccess(msg: String)

}