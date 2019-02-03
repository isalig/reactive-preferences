package alexi.rxpreferences

import android.content.SharedPreferences

class FloatPreference(preferences: SharedPreferences, key: String, defValue: Float = DEFAULT_VALUE) :
    Preference<Float>(preferences, key, defValue) {

    companion object {
        const val DEFAULT_VALUE = 0F
    }

    override fun getValue(): Float = preferences.getFloat(key, defValue)
}