package alexi.rxpreferences

import android.content.SharedPreferences
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

private const val KEY = "key"

@RunWith(MockitoJUnitRunner::class)
class PreferenceTest {

    private var currentValue = ""

    private lateinit var spyPreference: TestPreference

    @Mock
    private lateinit var sharedPreferences: SharedPreferences
    @Mock
    private lateinit var editor: SharedPreferences.Editor

    @Before
    fun setUp() {
        spyPreference = spy(TestPreference(sharedPreferences, KEY))

        `when`(spyPreference.preferences).thenReturn(sharedPreferences)
        `when`(sharedPreferences.edit()).thenReturn(editor)
        `when`(sharedPreferences.getString(eq(KEY), any())).then { currentValue }
        `when`(editor.putString(anyString(), anyString())).then {
            currentValue = it.arguments[1] as String
            spyPreference.onPreferenceChangedListener?.onSharedPreferenceChanged(
                sharedPreferences,
                it.arguments[0] as String
            )
            editor
        }
    }

    @Test
    fun `test source registers shared preferences update listener`() {
        spyPreference.toObservable().test()
        verify(sharedPreferences).registerOnSharedPreferenceChangeListener(any())
    }

    @Test
    fun `test source unregister preferences listener on dispose`() {
        val subscription = spyPreference.toObservable().test()
        subscription.dispose()
        assertNull(spyPreference.onPreferenceChangedListener)
    }

    @Test
    fun `test source emits value on subscribe`() {
        spyPreference.toObservable().test().valueCount() > 0
    }

    @Test
    fun `test source updates on preference change`() {
        spyPreference.toObservable().test().apply {
            sharedPreferences.edit().putString(KEY, "first")
            sharedPreferences.edit().putString(KEY, "second")
        }.assertValues("", "first", "second")
    }

    @Test
    fun `test source not updates when preference with different key changed`() {
        val source = spyPreference.toObservable().test()
        sharedPreferences.edit().putString("wrong key", "first")
        source.assertNoErrors()
        assertEquals(source.valueCount(), 1)
    }

    @Test
    fun `test source not emit repeated values`() {
        spyPreference.toObservable().test().apply {
            sharedPreferences.edit().putString(KEY, "first")
            sharedPreferences.edit().putString(KEY, "first")
            sharedPreferences.edit().putString(KEY, "second")
            sharedPreferences.edit().putString(KEY, "first")
        }.assertValues("", "first", "second", "first")
    }

    @Test
    fun `test source shares value between different subscribers`() {
        val values = arrayOf("first", "second", "third")

        val testObservable = spyPreference.toObservable()
        val firstSubscription = testObservable.test()
        sharedPreferences.edit().putString(KEY, values[0])
        sharedPreferences.edit().putString(KEY, values[1])
        val secondSubscription = testObservable.test()
        sharedPreferences.edit().putString(KEY, values[2])

        firstSubscription.assertValues("", values[0], values[1], values[2])
        secondSubscription.assertValues(values[1], values[2])
    }

    class TestPreference(
        sharedPreferences: SharedPreferences,
        key: String,
        defValue: String = ""
    ) : Preference<String>(
        sharedPreferences,
        key,
        defValue
    ) {
        override fun getValue(): String = preferences.getString(key, defValue)!!
    }
}