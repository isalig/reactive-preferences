package alexi.rxpreferences

import android.content.SharedPreferences

class LongPreference(preferences: SharedPreferences, key: String, defValue: Long) :
    Preference<Long>(preferences, key, defValue) {

    override fun getValue(): Long = preferences.getLong(key, defValue)
}