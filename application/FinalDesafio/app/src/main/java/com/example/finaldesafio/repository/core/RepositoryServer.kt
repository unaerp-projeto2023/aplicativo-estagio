package com.example.finaldesafio.repository.core

import com.example.finaldesafio.repository.extensions.serverWrapper
import com.example.finaldesafio.repository.model.User
import com.example.finaldesafio.repository.model.WsResult

class RepositoryServer {
    private var service = RetrofitMobile().apiService

    fun addUser(user: User, callback: (WsResult<User>) -> Unit) {
        service.addUser(0, user.name, user.password, user.email, user.type).serverWrapper { callback.invoke(it) }
    }

    fun getLogin(login: String, password: String, callback: (WsResult<User>) -> Unit) {
        service.getLogin(password, login).serverWrapper { callback.invoke(it) }
    }


}