package alexi.rxpreferences

import android.content.SharedPreferences

class StringSetPreference(preferences: SharedPreferences, key: String, defValue: MutableSet<String>) :
    Preference<MutableSet<String>>(preferences, key, defValue) {

    override fun getValue(): MutableSet<String> = preferences.getStringSet(key, defValue)!!
}