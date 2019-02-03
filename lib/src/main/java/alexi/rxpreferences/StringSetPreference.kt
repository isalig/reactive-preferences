package alexi.rxpreferences

import android.content.SharedPreferences

class StringSetPreference(preferences: SharedPreferences, key: String, defValue: MutableSet<String> = DEFAULT_VALUE) :
    Preference<MutableSet<String>>(preferences, key, defValue) {

    companion object {
        val DEFAULT_VALUE = HashSet<String>()
    }

    override fun getValue(): MutableSet<String> = preferences.getStringSet(key, defValue)!!
}