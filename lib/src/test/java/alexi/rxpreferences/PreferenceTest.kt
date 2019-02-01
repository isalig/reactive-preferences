package alexi.rxpreferences

import android.content.SharedPreferences
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PreferenceTest {

    @Mock
    private lateinit var preferencesMock: SharedPreferences
    private lateinit var target: Preference<Any>

    @Before
    fun setUp() {
        target = spy(object : Preference<Any>(preferencesMock, "", "") {
            override fun getValue(): Any? = preferences.getString("", "")
        })

        `when`(target.preferences).thenReturn(preferencesMock)
        `when`(preferencesMock.getString(anyString(), anyString())).thenReturn("")
    }

    @Test
    fun `test preference emits saved value on subscription`() {
        target.toObservable().test()
        verify(target, times(1)).getValue()
    }

    @Test
    fun `test dispose clears shared preferences subscription`() {
        val subscription = target.toObservable().test()
        subscription.dispose()
        assertNull(target.onPreferenceChangedListened)
    }

    @Test
    fun `test preference source not emitting default value if empty`() {
        `when`(preferencesMock.getString(anyString(), anyString())).thenReturn(null)
        target.toObservable().test().assertNoValues().assertNoErrors()
    }
}