package com.application.poem_poet.db

import android.content.SharedPreferences
import com.application.poem_poet.model.PoemHelp
import com.application.poem_poet.model.UserGeneralSave
import com.google.gson.Gson

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

    var saveUidUser: String?
        get() = sharedPreferences.getString(SAVE_UID_USER, "")
        set(value) {
            sharedPreferences.edit().putString(SAVE_UID_USER, value).apply()
        }

    var savePoemHelp: PoemHelp
        get() {
            return try {
                Gson().fromJson(
                    sharedPreferences.getString(SAVE_POEM_HELP, "") ?: "",
                    PoemHelp::class.java
                )
            } catch (e: Exception) {
                PoemHelp("", "", "", "", "", " ", "", "", 0)
            }
        }
        set(value) {
            sharedPreferences.edit().putString(SAVE_POEM_HELP, Gson().toJson(value)).apply()
        }

    var saveUserGeneral: UserGeneralSave
        get() {
            return try {
                Gson().fromJson(
                    sharedPreferences.getString(SAVE_USER_GENERAL, "") ?: "",
                    UserGeneralSave::class.java
                )
            } catch (e: Exception) {
                UserGeneralSave("", "", "", "", "", "")
            }
        }
        set(value) {
            sharedPreferences.edit().putString(SAVE_USER_GENERAL, Gson().toJson(value)).apply()
        }

    companion object {
        const val KEY_CHECK_DETAILED_FRAGMENT = "check_detailed_fragment"
        const val KEY_CHECK_CROP_FRAGMENT = "check_crop_fragment"
        const val SAVE_POEM_HELP = "save_poem_help"
        const val SAVE_USER_GENERAL = "save_user_general"
        const val SAVE_UID_USER = "save_uid_user"
    }
}
