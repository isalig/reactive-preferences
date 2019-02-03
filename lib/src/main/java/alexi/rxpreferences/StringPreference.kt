package alexi.rxpreferences

import android.content.SharedPreferences

class StringPreference(preferences: SharedPreferences, key: String, defValue: String = DEFAULT_VALUE) :
    Preference<String>(preferences, key, defValue) {

    companion object {
        const val DEFAULT_VALUE = ""
    }

    override fun getValue(): String = preferences.getString(key, DEFAULT_VALUE)!!
}