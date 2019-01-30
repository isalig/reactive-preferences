package alexi.rxpreferences

import android.content.SharedPreferences

class StringPreference(preferences: SharedPreferences, key: String, defValue: String) :
    Preference<String>(preferences, key, defValue) {

    override fun getValue(): String = preferences.getString(key, DEFAULT_STRING)!!

    companion object {
        const val DEFAULT_STRING = ""
    }
}