package com.example.patientrecord.config

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.patientrecord.data.Patient

class DbHelper(context: Context) {

    private val database = AppDataBase(context)

    companion object {
        private const val TAG = "DbHelper"

        private var dbHelper: DbHelper? = null

        fun getInstance(context: Context): DbHelper {
            if (dbHelper == null) {
                dbHelper = DbHelper(context)
            }
            return dbHelper!!
        }

    }

    fun savePatient(patient: Patient) {
        val dbWrite = database.writableDatabase
        val values = ContentValues()
        values.put("name", patient.name)
        values.put("gender", patient.gender)
        values.put("age", patient.age)
        values.put("mobile", patient.mobile)
        values.put("email", patient.email)
        values.put("address", patient.address)
        values.put("photo", patient.photo)

        if (patient.id > 0) {
            dbWrite.update(TABLE_PATIENT, values, "id = ${patient.id}", null)
        } else {
            dbWrite.insert(TABLE_PATIENT, null, values)
        }

        dbWrite.close()
    }

    @SuppressLint("Range")
    fun getPatientList(mPatients: MutableLiveData<ArrayList<Patient>>) {

        val list = ArrayList<Patient>()

        val dbRead = database.readableDatabase
        val cursor = dbRead.rawQuery("SELECT * FROM $TABLE_PATIENT", null)

        while (cursor.moveToNext()) {

            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val age = cursor.getInt(cursor.getColumnIndex("age"))
            val name = cursor.getString(cursor.getColumnIndex("name"))
            val mobile = cursor.getString(cursor.getColumnIndex("mobile"))
            val gender = cursor.getString(cursor.getColumnIndex("gender"))
            val address = cursor.getString(cursor.getColumnIndex("address"))
            val email = cursor.getString(cursor.getColumnIndex("email"))
            val photo = cursor.getString(cursor.getColumnIndex("photo"))
            val dateTime = cursor.getString(cursor.getColumnIndex("created_at"))

            val patient = Patient(id, name, age, gender, mobile, email, address, photo, dateTime)
            list.add(patient)
        }

        cursor.close()
        dbRead.close()
        mPatients.postValue(list)
    }

    fun deletePatient(id: Int) {
        val dbWrite = database.writableDatabase
        dbWrite.delete(TABLE_PATIENT,"id = $id", null)
        dbWrite.close()
        Log.e("database","Delete $id")
    }


}