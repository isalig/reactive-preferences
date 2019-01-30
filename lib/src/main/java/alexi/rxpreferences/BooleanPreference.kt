package alexi.rxpreferences

import android.content.SharedPreferences

class BooleanPreference(preferences: SharedPreferences, key: String, defValue: Boolean) :
    Preference<Boolean>(preferences, key, defValue) {

    override fun getValue(): Boolean = preferences.getBoolean(key, defValue)
}