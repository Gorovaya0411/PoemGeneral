package com.application.poem_poet.db

import android.content.SharedPreferences

class SessionStore(private val sharedPreferences: SharedPreferences) {

    var checkSound: Boolean
        get() = sharedPreferences.getBoolean(KEY_CHECK_SOUND, true)
        set(value) {
            sharedPreferences.edit().putBoolean(KEY_CHECK_SOUND, value).apply()
        }

    var checkNumHive: Int
        get() = sharedPreferences.getInt(KEY_CHECK_NUM_HiVE, 4)
        set(value) {
            sharedPreferences.edit().putInt(KEY_CHECK_NUM_HiVE, value).apply()
        }

    var score: Int
        get() = sharedPreferences.getInt(KEY_SCORE, 0)
        set(value) {
            sharedPreferences.edit().putInt(KEY_SCORE, value).apply()
        }

    var maxScore: Int
        get() = sharedPreferences.getInt(KEY_MAX_SCORE, 0)
        set(value) {
            sharedPreferences.edit().putInt(KEY_MAX_SCORE, value).apply()
        }

    var duration: Int
        get() = sharedPreferences.getInt(KEY_DURATION, 4000)
        set(value) {
            sharedPreferences.edit().putInt(KEY_DURATION, value).apply()
        }

    var quantityOpenCard: Int
        get() = sharedPreferences.getInt(KEY_QUANTITY_OPEN_CARD, 0)
        set(value) {
            sharedPreferences.edit().putInt(KEY_QUANTITY_OPEN_CARD, value).apply()
        }

    companion object {
        private const val KEY_DEPOSIT = "deposit"
        private const val KEY_REGISTER = "register"
        private const val KEY_UU_ID = "uuid"
        const val KEY_SAVED_URL = "saved_url"
        const val KEY_DEEP_LINK = "deeplink"
        const val KEY_TOKEN = "token"
        const val KEY_REMOTE_URL = "remote_url"
        const val FIRST_LAUNCH_APP = "first_launch_app"
        const val KEY_CHECK_SOUND = "check_shake"
        const val KEY_QUANTITY_OPEN_CARD = "key_quantity_open_card"
        const val KEY_CHECK_VIBRATION = "open_check_vibration"
        const val KEY_CHECK_NUM_HiVE = "open_check_num_hive"
        const val KEY_SCORE = "score"
        const val KEY_MAX_SCORE = "max_score"
        const val KEY_DURATION = "duration"
        const val KEY_CHECK_GAME_BG_STYLE = "check_game_bg_style"
    }
}
