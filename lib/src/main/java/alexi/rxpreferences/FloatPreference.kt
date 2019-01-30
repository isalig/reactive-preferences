package alexi.rxpreferences

import android.content.SharedPreferences

class FloatPreference(preferences: SharedPreferences, key: String, defValue: Float) :
    Preference<Float>(preferences, key, defValue) {

    override fun getValue(): Float = preferences.getFloat(key, defValue)
}