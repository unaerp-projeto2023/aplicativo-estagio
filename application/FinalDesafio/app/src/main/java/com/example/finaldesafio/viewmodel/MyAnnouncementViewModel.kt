package com.example.finaldesafio.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.finaldesafio.repository.core.RepositoryServer
import com.example.finaldesafio.repository.model.Announcement
import com.example.finaldesafio.repository.model.AnnouncementList
import com.example.finaldesafio.repository.model.AnnouncementSearch
import com.example.finaldesafio.repository.model.UserSession
import com.example.finaldesafio.repository.model.WsResult

class MyAnnouncementViewModel : ViewModel() {
    private var repository: RepositoryServer? = null

    var userSession: UserSession? = null
    val response: MutableLiveData<WsResult<AnnouncementList>> by lazy { MutableLiveData<WsResult<AnnouncementList>>() }
    val saved: MutableLiveData<WsResult<Nothing>> by lazy { MutableLiveData<WsResult<Nothing>>() }

    fun initViewModel(context: Context?, callback: () -> Unit) {
        context?.let {
            repository  = RepositoryServer()
            userSession = UserSession(it)
        }
        callback.invoke()
    }

    fun getList(data: AnnouncementSearch) {
        response.removeObserver {}
        repository?.announcementList(data) { response.postValue(it) }
    }

    fun getSelectAnnouncement(announcementId: Long) {
        saved.removeObserver {}
        repository?.selection(announcementId, userSession?.userId!!) {
            saved.postValue(it)
        }
    }
}