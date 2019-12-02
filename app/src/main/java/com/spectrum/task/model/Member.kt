package com.spectrum.task.model


import com.google.gson.annotations.SerializedName

data class Member(
    @SerializedName("age")
    var age: Int,
    @SerializedName("email")
    var email: String,
    @SerializedName("_id")
    var id: String,
    @SerializedName("name")
    var name: Name,
    @SerializedName("phone")
    var phone: String
)