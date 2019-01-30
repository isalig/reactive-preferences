package alexi.rxpreferences

import android.content.SharedPreferences

class RxPrefs(private val sharedPreferences: SharedPreferences) : RxPreferences {

    override fun getString(key: String, defValue: String) =
        StringPreference(sharedPreferences, key, defValue)

    override fun getStringSet(key: String, defValue: MutableSet<String>) =
        StringSetPreference(sharedPreferences, key, defValue)

    override fun getInt(key: String, defValue: Int) =
        IntPreference(sharedPreferences, key, defValue)

    override fun getLong(key: String, defValue: Long) =
        LongPreference(sharedPreferences, key, defValue)

    override fun getFloat(key: String, defValue: Float) =
        FloatPreference(sharedPreferences, key, defValue)

    override fun getBoolean(key: String, defValue: Boolean) =
        BooleanPreference(sharedPreferences, key, defValue)
}