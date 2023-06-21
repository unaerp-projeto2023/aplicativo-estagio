package com.example.finaldesafio.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.finaldesafio.repository.core.RepositoryServer
import com.example.finaldesafio.repository.model.User
import com.example.finaldesafio.repository.model.UserSession
import com.example.finaldesafio.repository.model.WsResult

class AddUserViewModel : ViewModel() {
    private var repository: RepositoryServer? = null
    private var userSession: UserSession? = null

    val response: MutableLiveData<WsResult<User>> by lazy { MutableLiveData<WsResult<User>>() }

    fun initViewModel(context: Context?, callback: () -> Unit) {
        context?.let {
            repository  = RepositoryServer()
            userSession = UserSession(it)
        }
        callback.invoke()
    }

    fun saveUser(user: User) {
        response.removeObserver {}
        repository?.addUser(user) { response.postValue(it) }
    }

    fun userClear() { userSession?.clear() }
    fun setUserLogin(user: User?) { userSession?.save(user) }
}