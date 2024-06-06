package com.caresaas.app.util.helpers

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CareSaasSharePreference @Inject constructor(@ApplicationContext context: Context) {

    companion object {
        private const val TOKEN_KEY = "TOKEN"
    }


    private var sharedPreferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    var token: String
        get() = sharedPreferences.getString(TOKEN_KEY, "") ?: ""
        set(value) {
            sharedPreferences.edit().putString(TOKEN_KEY, value).apply()
        }

    fun setString(key: String, value: String) {
        sharedPreferences.edit {
            putString(key, value)
            apply()
        }
    }

    fun setBoolean(key: String, value: Boolean) {
        sharedPreferences.edit {
            putBoolean(key, value)
            apply()
        }
    }

    fun setLong(key: String, value: Long) {
        sharedPreferences.edit {
            putLong(key, value)
            apply()
        }
    }

    fun setInt(key: String, value: Int) {
        sharedPreferences.edit {
            putInt(key, value)
            apply()
        }
    }

    fun getString(key: String, defaultString: String? = null) = sharedPreferences.getString(key, defaultString) ?: ""
    fun getInt(key: String, defaultInt: Int = -1) = sharedPreferences.getInt(key, defaultInt)

    fun getBoolean(key: String, defaultBoolean: Boolean = false) = sharedPreferences.getBoolean(key, defaultBoolean)

    fun getLong(key: String, defaulLong: Long = 0L) = sharedPreferences.getLong(key, defaulLong)

    fun clear(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }
}