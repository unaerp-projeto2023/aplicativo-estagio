package com.example.finaldesafio.repository.preferences

import android.content.Context
import androidx.preference.PreferenceManager

class SharedPreferencesImpl(private val context: Context) : Preferences {
    override fun getValue(key: String, defaultValue: String): String   = getFromPrefs(key, defaultValue)
    override fun getValue(key: String, defaultValue: Boolean): Boolean = getFromPrefs(key, defaultValue)
    override fun getValue(key: String, defaultValue: Int): Int         = getFromPrefs(key, defaultValue)
    override fun getLongValue(key: String, defaultValue: Long): Long   = getFromPrefs(key, defaultValue)

    override fun setValue(key: String, value: String)   { saveToPrefs(key, value) }
    override fun setValue(key: String, value: Boolean)  { saveToPrefs(key, value) }
    override fun setValue(key: String, value: Int)      { saveToPrefs(key, value) }
    override fun setLongValue(key: String, value: Long) { saveToPrefs(key, value) }

    /**
     * Save Preferences
     */
    private fun saveToPrefs(key: String, value: String) {
        PreferenceManager.getDefaultSharedPreferences(context).also {
            it.edit().apply {
                putString(key, value)
                apply()
            }
        }
    }
    private fun saveToPrefs(key: String, value: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(context).also {
            it.edit().apply {
                putBoolean(key, value)
                apply()
            }
        }
    }
    private fun saveToPrefs(key: String, value: Int) {
        PreferenceManager.getDefaultSharedPreferences(context).also {
            it.edit().apply {
                putInt(key, value)
                apply()
            }
        }
    }

    private fun saveToPrefs(key: String, value: Long) {
        PreferenceManager.getDefaultSharedPreferences(context).also {
            it.edit().apply {
                putLong(key, value)
                apply()
            }
        }
    }

    /**
     * Get Preferences
     */
    private fun getFromPrefs(key: String, defaultValue: String): String {
        PreferenceManager.getDefaultSharedPreferences(context).apply {
            return getString(key, defaultValue).toString()
        }
    }
    private fun getFromPrefs(key: String, defaultValue: Boolean): Boolean {
        PreferenceManager.getDefaultSharedPreferences(context).apply {
            return getBoolean(key, defaultValue)
        }
    }
    private fun getFromPrefs(key: String, defaultValue: Int): Int {
        PreferenceManager.getDefaultSharedPreferences(context).apply {
            return getInt(key, defaultValue)
        }
    }
    private fun getFromPrefs(key: String, defaultValue: Long): Long {
        PreferenceManager.getDefaultSharedPreferences(context).apply {
            return getLong(key, defaultValue)
        }
    }
}