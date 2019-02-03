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

    private var lastValue: T? = null

    var onPreferenceChangedListener: OnSharedPreferenceChangeListener? = null
        private set

    abstract fun getValue(): T

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
        OnSharedPreferenceChangeListener { _, key ->
            if (this.key == key) {
                emitter.emitValue()
            }
        }.also {
            onPreferenceChangedListener = it
        }

    private fun Emitter<T>.emitValue() {
        val currentValue = getValue()

        if (lastValue == currentValue) {
            return
        }

        lastValue = currentValue
        onNext(currentValue)
    }

    private fun onDispose() {
        preferences.unregisterOnSharedPreferenceChangeListener(onPreferenceChangedListener)
        onPreferenceChangedListener = null
    }
}