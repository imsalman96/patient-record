package com.example.patientrecord.data

import java.io.Serializable
import java.time.chrono.ChronoLocalDateTime

class Patient(
    val name : String,
    val age : Int,
    val gender : String,
    val mobile : String,
    val email : String,
    val address : String,
    val photo : String
): Serializable {

    var id = 0
    var dateTime = ""

    constructor(id: Int, name: String,age: Int, gender: String, mobile: String, email: String, address: String, photo: String, dateTime: String): this(name, age, gender, mobile, email, address, photo){
        this.id = id
        this.dateTime = dateTime
    }

}