package com.application.poem_poet.db

import android.content.SharedPreferences
import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.model.User
import com.application.poem_poet.model.UserGeneral
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

    var savePoemAnswer: PoemAnswer
        get() {
            return try {
                Gson().fromJson(
                    sharedPreferences.getString(SAVE_POEM_ANSWER, "") ?: "",
                    PoemAnswer::class.java
                )
            } catch (e: Exception) {
                PoemAnswer()
            }
        }
        set(value) {
            sharedPreferences.edit().putString(SAVE_POEM_ANSWER, Gson().toJson(value)).apply()
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

    var saveUser: User
        get() {
            return try {
                Gson().fromJson(
                    sharedPreferences.getString(SAVE_USER, "") ?: "",
                    User::class.java
                )
            } catch (e: Exception) {
                User("", "", "", "", "", "")
            }
        }
        set(value) {
            sharedPreferences.edit().putString(SAVE_USER, Gson().toJson(value)).apply()
        }

    companion object {
        const val KEY_CHECK_DETAILED_FRAGMENT = "check_detailed_fragment"
        const val KEY_CHECK_CROP_FRAGMENT = "check_crop_fragment"
        const val SAVE_POEM_ANSWER = "save_poem_answer"
        const val SAVE_USER = "save_user"
        const val SAVE_USER_GENERAL = "save_user_general"
    }
}
