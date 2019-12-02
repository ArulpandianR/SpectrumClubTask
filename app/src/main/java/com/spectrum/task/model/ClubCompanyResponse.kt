package com.spectrum.task.model


import com.google.gson.annotations.SerializedName


data class ClubCompany(
    @SerializedName("about")
    var about: String,
    @SerializedName("company")
    var company: String,
    @SerializedName("_id")
    var id: String,
    @SerializedName("logo")
    var logo: String,
    @SerializedName("members")
    var members: List<Member>,
    @SerializedName("website")
    var website: String,
    var isFav: Boolean = false,
    var isfollowed: Boolean = false
)

