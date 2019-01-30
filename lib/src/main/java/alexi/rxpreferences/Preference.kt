package alexi.rxpreferences

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import io.reactivex.Emitter
import io.reactivex.Observable
import io.reactivex.ObservableEmitter

abstract class Preference<T>(
    protected val preferences: SharedPreferences,
    val key: String,
    val defValue: T
) {

    private var onPreferenceChangedListened: OnSharedPreferenceChangeListener? = null

    abstract fun getValue(): T

    fun toObservable(): Observable<T> = Observable.create<T> {
        it.prepareObservable()
    }.doOnDispose {
        onDispose()
    }

    private fun ObservableEmitter<T>.prepareObservable() {
        try {
            onNext(getValue())
            preferences.registerOnSharedPreferenceChangeListener(
                createOnPreferenceChangedListener(this)
            )
        } catch (e: Exception) {
            onError(e)
            onDispose()
        }
    }

    private fun createOnPreferenceChangedListener(emitter: Emitter<T>) =
        OnSharedPreferenceChangeListener { _, _ ->
            emitter.onNext(getValue())
        }.also {
            onPreferenceChangedListened = it
        }

    private fun onDispose() {
        preferences.unregisterOnSharedPreferenceChangeListener(onPreferenceChangedListened)
        onPreferenceChangedListened = null
    }
}