package com.spectrum.task.model


import com.google.gson.annotations.SerializedName

data class Name(
    @SerializedName("first")
    var first: String,
    @SerializedName("last")
    var last: String
)