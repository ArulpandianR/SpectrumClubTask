package com.spectrum.task.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.spectrum.task.model.Member

class MemberViewModel(application: Application) : AndroidViewModel(application) {

    var memberList = MutableLiveData<List<Member>>().apply { value = emptyList() }

    fun getFilteredList(searchText: String): List<Member>? {
        return memberList.value?.filter {
            (it.name.first + it.name.last).toLowerCase().contains(searchText.toLowerCase())
        }
    }

}
