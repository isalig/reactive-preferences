package alexi.rxpreferences

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import androidx.annotation.VisibleForTesting
import io.reactivex.Emitter
import io.reactivex.Observable
import io.reactivex.ObservableEmitter

abstract class Preference<T>(
    val preferences: SharedPreferences,
    val key: String,
    val defValue: T
) {

    var onPreferenceChangedListened: OnSharedPreferenceChangeListener? = null
        private set

    abstract fun getValue(): T?

    fun toObservable(): Observable<T> = Observable.create<T> {
        it.prepareObservable()
    }.doOnDispose {
        onDispose()
    }

    @VisibleForTesting
    private fun ObservableEmitter<T>.prepareObservable() {
        try {
            preferences.registerOnSharedPreferenceChangeListener(
                createOnPreferenceChangedListener(this)
            )
            emitValue()
        } catch (e: Exception) {
            onError(e)
            onDispose()
        }
    }

    private fun createOnPreferenceChangedListener(emitter: Emitter<T>) =
        OnSharedPreferenceChangeListener { _, _ ->
            emitter.emitValue()
        }.also {
            onPreferenceChangedListened = it
        }

    private fun Emitter<T>.emitValue() {
        getValue()?.let { onNext(it) }
    }

    private fun onDispose() {
        preferences.unregisterOnSharedPreferenceChangeListener(onPreferenceChangedListened)
        onPreferenceChangedListened = null
    }
}