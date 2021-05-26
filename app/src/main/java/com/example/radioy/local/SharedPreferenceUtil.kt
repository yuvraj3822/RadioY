package com.example.radioy.local

import android.content.Context
import android.content.SharedPreferences

/**
 * It is is used to store the data
 * in key and value form. Practically its mainly used to store
 * small temporary data even when the app is killed
 */

class SharedPreferenceUtil(context: Context) {
    private var _prefs: SharedPreferences? = null
    private var _editor: SharedPreferences.Editor? = null
    fun getString(key: String?, defaultvalue: String?): String? {
        return if (_prefs == null) {
            defaultvalue
        } else _prefs?.getString(key, defaultvalue)
    }

    fun setString(key: String?, value: String?) {
        if (_editor == null) {
            return
        }
        _editor?.putString(key, value)
    }

    fun getBoolean(key: String?, defaultvalue: Boolean): Boolean {
        return if (_prefs == null) {
            defaultvalue
        } else _prefs!!.getBoolean(key, defaultvalue)
    }

    fun setBoolean(key: String?, value: Boolean?) {
        if (_editor == null) {
            return
        }
        _editor!!.putBoolean(key, value!!)
    }

    fun getInt(key: String?, defaultvalue: Int): Int {
        return if (_prefs == null) {
            defaultvalue
        } else _prefs!!.getInt(key, defaultvalue)
    }

    fun setInt(key: String?, value: Int) {
        if (_editor == null) {
            return
        }
        _editor!!.putInt(key, value)
    }

    fun clearAll() {
        if (_editor == null) {
            return
        }
        _editor!!.clear().commit()
    }

    fun removeOneItem(key: String?) {
        if (_editor == null) {
            return
        }
        _editor!!.remove(key)
    }

    fun save() {
        if (_editor == null) {
            return
        }
        _editor!!.commit()
    }

    init {
        _prefs = context.getSharedPreferences(
            "RADIO",
            Context.MODE_PRIVATE
        )
        _editor = _prefs?.edit()
    }
}