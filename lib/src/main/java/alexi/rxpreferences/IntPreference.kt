package alexi.rxpreferences

import android.content.SharedPreferences

class IntPreference(preferences: SharedPreferences, key: String, defValue: Int = DEFAULT_VALUE) :
    Preference<Int>(preferences, key, defValue) {

    override fun getValue(): Int = preferences.getInt(key, defValue)

    companion object {
        const val DEFAULT_VALUE = 0
    }
}