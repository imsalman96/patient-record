package com.example.patientrecord

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.patientrecord.config.ApiRepository
import com.example.patientrecord.config.DbHelper
import com.example.patientrecord.data.Patient
import com.example.patientrecord.utils.UIApiCallResponseListener
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private lateinit var dbHelper: DbHelper
    val mPatients = MutableLiveData<ArrayList<Patient>>()
    private lateinit var apiRepository: ApiRepository

    fun init(context: Context) {
        apiRepository = ApiRepository()
        dbHelper = DbHelper.getInstance(context)
    }


    fun savePatient(patient: Patient) {
        dbHelper.savePatient(patient)
    }

    fun loadPatientList() {
        dbHelper.getPatientList(mPatients)
    }

    fun deletePatient(id: Int) {
        dbHelper.deletePatient(id)
    }

    fun registerPatient(
        context: Context,
        patient: Patient,
        uiApiCallResponseListener: UIApiCallResponseListener
    ) {
       viewModelScope.launch {
           apiRepository.registerPatient(context, patient, uiApiCallResponseListener)
       }
    }
}