package com.example.finaldesafio.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel

import com.example.finaldesafio.repository.model.User
import com.example.finaldesafio.repository.model.UserSession

class MainViewModel : ViewModel() {
    var userSession: UserSession? = null

    fun initViewModel(context: Context?, callback: () -> Unit) {
        userSession = context?.let { UserSession(it) }
        callback.invoke()
    }

    fun userClear() { userSession?.clear() }
}