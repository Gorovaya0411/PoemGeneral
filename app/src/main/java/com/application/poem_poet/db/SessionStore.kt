package com.application.poem_poet.db

import android.content.SharedPreferences

class SessionStore(private val sharedPreferences: SharedPreferences) {

    var checkDetailedFragment: String?
        get() = sharedPreferences.getString(KEY_CHECK_DETAILED_FRAGMENT, "FromList")
        set(value) {
            sharedPreferences.edit().putString(KEY_CHECK_DETAILED_FRAGMENT, value).apply()
        }

    var checkCropFragment: String?
        get() = sharedPreferences.getString(KEY_CHECK_CROP_FRAGMENT, "")
        set(value) {
            sharedPreferences.edit().putString(KEY_CHECK_CROP_FRAGMENT, value).apply()
        }

    var saveNamePoetAddFragment: String?
        get() = sharedPreferences.getString(SAVE_NAME_POET_ADD_FRAGMENT, "")
        set(value) {
            sharedPreferences.edit().putString(SAVE_NAME_POET_ADD_FRAGMENT, value).apply()
        }

    var saveIdPoetAddFragment: String?
        get() = sharedPreferences.getString(SAVE_ID_POET_ADD_FRAGMENT, "")
        set(value) {
            sharedPreferences.edit().putString(SAVE_ID_POET_ADD_FRAGMENT, value).apply()
        }

    var saveLoginUser: String?
        get() = sharedPreferences.getString(SAVE_LOGIN_USER, "")
        set(value) {
            sharedPreferences.edit().putString(SAVE_LOGIN_USER, value).apply()
        }

    var saveIdUser: String?
        get() = sharedPreferences.getString(SAVE_ID_USER, "")
        set(value) {
            sharedPreferences.edit().putString(SAVE_ID_USER, value).apply()
        }

    companion object {
        const val KEY_CHECK_DETAILED_FRAGMENT = "check_detailed_fragment"
        const val KEY_CHECK_CROP_FRAGMENT = "check_crop_fragment"
        const val SAVE_NAME_POET_ADD_FRAGMENT = "save_name_poet_add_fragment"
        const val SAVE_ID_POET_ADD_FRAGMENT = "save_id_poet_add_fragment"
        const val SAVE_LOGIN_USER = "save_login_user"
        const val SAVE_ID_USER = "save_id_user"
    }
}
