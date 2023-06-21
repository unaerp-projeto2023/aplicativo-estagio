package com.example.finaldesafio.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.finaldesafio.repository.core.RepositoryServer
import com.example.finaldesafio.repository.model.User
import com.example.finaldesafio.repository.model.UserSession
import com.example.finaldesafio.repository.model.WsResult

class LoginViewModel : ViewModel() {
    private var repository: RepositoryServer? = null

    var userSession: UserSession? = null
    val response: MutableLiveData<WsResult<User>> by lazy { MutableLiveData<WsResult<User>>() }

    fun initViewModel(context: Context?, callback: () -> Unit) {
        context?.let {
            repository  = RepositoryServer()
            userSession = UserSession(it)
        }
        callback.invoke()
    }

    fun getLogin(userLogin: String, userPassword: String) {
        response.removeObserver {}
        repository?.getLogin(userLogin, userPassword) { response.postValue(it) }
    }

    fun userClear() { userSession?.clear() }
    fun setUserLogin(user: User?) { userSession?.save(user) }
}