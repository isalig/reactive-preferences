package alexi.rxpreferences

interface RxPreferences {

    fun getString(key: String, defValue: String = ""): Preference<String>
    fun getStringSet(key: String, defValue: MutableSet<String> = HashSet()): Preference<MutableSet<String>>
    fun getInt(key: String, defValue: Int = -1): Preference<Int>
    fun getLong(key: String, defValue: Long = -1L): Preference<Long>
    fun getFloat(key: String, defValue: Float = -1f): Preference<Float>
    fun getBoolean(key: String, defValue: Boolean = false): Preference<Boolean>
}