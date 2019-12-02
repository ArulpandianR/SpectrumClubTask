package com.spectrum.task.ui.main

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.spectrum.task.api.ClubApi
import com.spectrum.task.api.NetworkModule
import com.spectrum.task.helper.TaskHelper
import com.spectrum.task.model.ClubCompany
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch


class ClubViewModel(application: Application) : AndroidViewModel(application) {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    var companyList = MutableLiveData<List<ClubCompany>>().apply { value = emptyList() }

    val isLoadingLiveData = MediatorLiveData<Boolean>().apply {
        value = true
    }

    /**
     * To get club company details using coroutines
     */
    fun loadData(application: Application) {
        viewModelScope.launch {
            callRemoteRepo(application)
        }
    }

    /**
     * make service call using retrofit
     */
    private fun callRemoteRepo(application: Application) {
        if (TaskHelper.isInternetAvailable(application)) {
            val requestInterface = NetworkModule.provideRetrofit(
                NetworkModule.provideOkHttpClient()
            ).create(ClubApi::class.java)
            compositeDisposable.add(
                requestInterface.getClubCompanyDetails().observeOn(
                    AndroidSchedulers.mainThread()
                )?.subscribeOn(
                    Schedulers.io()
                )?.subscribe(
                    this::onTaskSuccess,
                    this::onTaskError
                )!!
            )
        } else {
            Toast.makeText(getApplication(), "Please Network Connection", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onTaskSuccess(clubResponseList: List<ClubCompany>) {
        if (!clubResponseList.isNullOrEmpty()) {
            companyList.value = clubResponseList
        } else {
            Toast.makeText(getApplication(), "Please Try Again", Toast.LENGTH_SHORT).show()
        }
        isLoadingLiveData.value = false
    }

    private fun onTaskError(error: Throwable) {
        Toast.makeText(getApplication(), error.toString(), Toast.LENGTH_SHORT).show()
    }

    /**
     * Implemented search filter
     */
    fun getFilteredList(searchText: String): List<ClubCompany>? {
        return companyList.value?.filter {
            it.company.toLowerCase().contains(searchText.toLowerCase())
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


}
