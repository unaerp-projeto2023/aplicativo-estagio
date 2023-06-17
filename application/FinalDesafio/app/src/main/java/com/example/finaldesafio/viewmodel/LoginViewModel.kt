package com.example.finaldesafio.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.finaldesafio.repository.core.RepositoryServer
import com.example.finaldesafio.repository.model.User
import com.example.finaldesafio.repository.model.WsResult

class LoginViewModel : ViewModel() {
    private var repository: RepositoryServer? = null
    val response: MutableLiveData<WsResult<User>> by lazy { MutableLiveData<WsResult<User>>() }

    fun initViewModel(context: Context?, callback: () -> Unit) {
        context?.let {
            repository  = RepositoryServer()
        }
        callback.invoke()
    }

    fun getLogin(login: String, password: String) {
        response.removeObserver {}
        repository?.getLogin(password, login) { response.postValue(it) }
    }
}