package com.example.finaldesafio.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.finaldesafio.repository.core.RepositoryServer
import com.example.finaldesafio.repository.model.Announcement
import com.example.finaldesafio.repository.model.UserSession
import com.example.finaldesafio.repository.model.WsResult

class AddAnnouncementViewModel : ViewModel() {
    private var repository: RepositoryServer? = null

    var userSession: UserSession? = null
    val response: MutableLiveData<WsResult<Nothing>> by lazy { MutableLiveData<WsResult<Nothing>>() }

    fun initViewModel(context: Context?, callback: () -> Unit) {
        context?.let {
            repository  = RepositoryServer()
            userSession = UserSession(it)
        }
        callback.invoke()
    }

    fun saveAnnouncement(data: Announcement) {
        response.removeObserver {}
        repository?.addAnnouncement(data) { response.postValue(it) }
    }
}