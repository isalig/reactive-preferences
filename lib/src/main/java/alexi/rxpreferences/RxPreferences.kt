package alexi.rxpreferences

interface RxPreferences {

    fun getString(key: String, defValue: String = StringPreference.DEFAULT_VALUE): Preference<String>

    fun getStringSet(
        key: String,
        defValue: MutableSet<String> = StringSetPreference.DEFAULT_VALUE
    ): Preference<MutableSet<String>>

    fun getInt(key: String, defValue: Int = IntPreference.DEFAULT_VALUE): Preference<Int>

    fun getLong(key: String, defValue: Long = LongPreference.DEFAULT_VALUE): Preference<Long>

    fun getFloat(key: String, defValue: Float = FloatPreference.DEFAULT_VALUE): Preference<Float>

    fun getBoolean(key: String, defValue: Boolean = BooleanPreference.DEFAULT_VALUE): Preference<Boolean>
}