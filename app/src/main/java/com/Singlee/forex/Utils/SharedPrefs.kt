package com.Singlee.forex.Utils


import android.content.SharedPreferences
import com.Singlee.forex.DataModels.SettingData
import com.Singlee.forex.DataModels.UserData
import javax.inject.Inject


class SharedPrefs @Inject constructor(var sharePref: SharedPreferences) {
    /**
     * Function which will save the integer value to preference with given key
     */
    fun save(preferenceKey: String, integerValue: Int) {
        saveToPreference(
            preferenceKey,
            integerValue
        )
    }

    /**
     * Function which will save the double value to preference with given key
     */
    fun save(preferenceKey: String, doubleValue: Float) {
        saveToPreference(
            preferenceKey,
            doubleValue
        )
    }

    /**
     * Function which will save the long value to preference with given key
     */
    fun save(preferenceKey: String, longValue: Long) {
        saveToPreference(
            preferenceKey,
            longValue
        )
    }

    /**
     * Function which will save the boolean value to preference with given key
     */
    fun save(preferenceKey: String, booleanValue: Boolean) {
        saveToPreference(
            preferenceKey,
            booleanValue
        )
    }

    /**
     * Function which will save the string value to preference with given key
     */
    fun save(preferenceKey: String, stringValue: String?) {
        stringValue?.let {
            saveToPreference(
                preferenceKey,
                stringValue
            )
        }
    }

    /**
     * General function to save preference value
     */
    private fun saveToPreference(key: String, value: Any) {
        sharePref.let {
            with(it.edit()) {
                when (value) {
                    is Int -> putInt(key, value)
                    is Float -> putFloat(key, value)
                    is Long -> putLong(key, value)
                    is Boolean -> putBoolean(key, value)
                    is String -> putString(key, value)
                }
                apply()
            }
        }
    }

    /**
     * Function which will return the integer value saved in the preference corresponds to the given preference key
     */
    fun getInt(preferenceKey: String): Int {
        return sharePref.getInt(preferenceKey, 0) ?: 0
    }

    /**
     * Function which will return the float value saved in the preference corresponds to the given preference key
     */
    fun getFloat(preferenceKey: String): Float {
        return sharePref.getFloat(preferenceKey, 0f) ?: 0f
    }

    /**
     * Function which will return the long value saved in the preference corresponds to the given preference key
     */
    fun getLong(preferenceKey: String): Long {
        return sharePref.getLong(preferenceKey, 0L) ?: 0L
    }

    /**
     * Function which will return the boolean value saved in the preference corresponds to the given preference key
     */
    fun getBoolean(preferenceKey: String): Boolean {
        return sharePref.getBoolean(preferenceKey, false)
    }

    /**
     * Function which will return the string value saved in the preference corresponds to the given preference key
     */
    fun getString(preferenceKey: String): String? {
        return sharePref.getString(preferenceKey, "") ?: ""
    }

    fun remove(preferenceKey: String) {
        sharePref.let {
            with(it.edit()) {
                remove(preferenceKey)
                apply()
            }
        }
    }

    fun clearPreference() {
        sharePref.let {
            with(it.edit()) {
                clear()
                apply()
            }
        }
    }

    fun saveUser(data: UserData) {
        save(Constant.LOGGED_IN,true)
        save(Constant.USER_ID,data.vid)
        save(Constant.NAME,data.name)
        save(Constant.EMAIL,data.email)
        save(Constant.SUBSCRIBED,data.subscription)
        save(Constant.IMAGE_URL,data.profileImage)
        save(Constant.PASSWORD,data.password)
        save(Constant.IS_THIRDPARTY,data.is_third_party)
        // profile setting
        save(Constant.chat_Notifications,data.settingData.chat_Notifications)
        save(Constant.support_Notifications,data.settingData.support_Notifications)
        save(Constant.progress_Notifications,data.settingData.progress_Notifications)
        save(Constant.offer_Notifications,data.settingData.offer_Notifications)
        save(Constant.team_Notifications,data.settingData.team_Notifications)
    }


    fun saveSetting(data: SettingData)
    {
        save(Constant.chat_Notifications,data.chat_Notifications)
        save(Constant.support_Notifications,data.support_Notifications)
        save(Constant.progress_Notifications,data.progress_Notifications)
        save(Constant.offer_Notifications,data.offer_Notifications)
        save(Constant.team_Notifications,data.team_Notifications)
    }

    fun getProfilePref() : SettingData
    {
        return SettingData(
            chat_Notifications = getBoolean(Constant.chat_Notifications),
            support_Notifications = getBoolean(Constant.support_Notifications),
            progress_Notifications = getBoolean(Constant.progress_Notifications),
            offer_Notifications = getBoolean(Constant.offer_Notifications),
            team_Notifications = getBoolean(Constant.team_Notifications)
        )

    }

    fun getUser():UserData
    {
        return UserData(
            vid =  getString(Constant.USER_ID)!!,
            name =  getString(Constant.NAME)!!,
            email =  getString(Constant.EMAIL)!!,
            password =  getString(Constant.PASSWORD)!!,
            is_third_party = getBoolean(Constant.IS_THIRDPARTY)
        )
    }

    fun setSubscribed(subscribed:Boolean){
        save(Constant.SUBSCRIBED,subscribed)
    }
    fun isSubscribed():Boolean{
        return getBoolean(Constant.SUBSCRIBED)
    }

    fun getMessageId(): String? {
        return getString(Constant.MESSAGE_ID)
    }


}