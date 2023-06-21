package com.example.finaldesafio.repository.preferences

interface Preferences {
    fun getValue(key: String, defaultValue: String) : String
    fun getValue(key: String, defaultValue: Boolean) : Boolean
    fun getValue(key: String, defaultValue: Int) : Int
    fun getLongValue(key: String, defaultValue: Long) : Long

    fun setValue(key: String, value: String)
    fun setValue(key: String, value: Boolean)
    fun setValue(key: String, value: Int)
    fun setLongValue(key: String, value: Long)
}