package com.example.finaldesafio.repository.model

import android.content.Context

import com.example.finaldesafio.repository.preferences.Preferences
import com.example.finaldesafio.repository.preferences.SharedPreferencesImpl

class UserSession constructor(private val context: Context) {
    private val preferences: Preferences = SharedPreferencesImpl(context)

    fun clear() {
        preferences.setValue(USER_EMAIL , "")
        preferences.setValue(USER_NAME  , "")
        preferences.setValue(USER_TYPE  , "")
        preferences.setLongValue(USER_ID,  0)
    }

    fun clearLogin() { preferences.setValue(USER_EMAIL, "") }
    fun clearName()  { preferences.setValue(USER_NAME, "") }

    fun setLogin(userEmail: String) { preferences.setValue(USER_EMAIL, userEmail) }

    fun save(user: User?) {
        user?.let {
            preferences.setValue(USER_EMAIL, it.userEmail.toString())
            preferences.setValue(USER_NAME , it.userName.toString())
            preferences.setValue(USER_TYPE , it.userType.toString())
            it.userId.let { it1 -> preferences.setLongValue(USER_ID, it1) }
        }
    }

    val userId    : Long   get() = preferences.getLongValue(USER_ID, 0)
    val userEmail : String get() = preferences.getValue(USER_EMAIL, "")
    val userName  : String get() = preferences.getValue(USER_NAME , "")
    val userType  : String get() = preferences.getValue(USER_TYPE , "")

    companion object {
        private const val USER_ID    = "userId"
        private const val USER_EMAIL = "userEmail"
        private const val USER_NAME  = "userName"
        private const val USER_TYPE  = "userType"
    }
}