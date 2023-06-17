package com.example.finaldesafio.repository.core

object Constants {
    const val API_URL = "http://desafio.monteoliva.net.br"
    const val TYPE    = "type"

    enum class AccountAction {
        ADD_USER,
        ADD_COMPANY,
        EDIT_USER,
        EDIT_COMPANY
    }
}