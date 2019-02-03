package alexi.rxpreferences

import android.content.SharedPreferences

class LongPreference(preferences: SharedPreferences, key: String, defValue: Long = DEFAULT_VALUE) :
    Preference<Long>(preferences, key, defValue) {

    companion object {
        const val DEFAULT_VALUE = 0L
    }

    override fun getValue(): Long = preferences.getLong(key, defValue)
}