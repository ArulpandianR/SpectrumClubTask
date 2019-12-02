package com.spectrum.task.api

import com.spectrum.task.model.ClubCompany
import io.reactivex.Observable
import retrofit2.http.GET

interface ClubApi {
    @GET("api/json/get/Vk-LhK44U")
    fun getClubCompanyDetails(): Observable<List<ClubCompany>>
}

interface AdapterOnClick {
    fun onClick(item: Any)
}