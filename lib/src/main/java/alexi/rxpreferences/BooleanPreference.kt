package alexi.rxpreferences

import android.content.SharedPreferences

class BooleanPreference(preferences: SharedPreferences, key: String, defValue: Boolean = DEFAULT_VALUE) :
    Preference<Boolean>(preferences, key, defValue) {

    companion object {
        const val DEFAULT_VALUE = false
    }

    override fun getValue(): Boolean = preferences.getBoolean(key, defValue)
}