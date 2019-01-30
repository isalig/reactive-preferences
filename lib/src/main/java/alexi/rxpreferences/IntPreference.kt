package alexi.rxpreferences

import android.content.SharedPreferences

class IntPreference(preferences: SharedPreferences, key: String, defValue: Int) :
    Preference<Int>(preferences, key, defValue) {

    override fun getValue(): Int = preferences.getInt(key, defValue)
}