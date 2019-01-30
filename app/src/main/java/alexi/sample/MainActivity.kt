package alexi.sample

import alexi.rxpreferences.RxPrefs
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

private const val KEY_STRING = "saved_string"

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    private lateinit var rxPreferences: RxPrefs

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefs = PreferenceManager.getDefaultSharedPreferences(this)
        rxPreferences = RxPrefs(prefs)
        btnSubmit.setOnClickListener {
            saveString(inputField?.text?.toString())
        }
    }

    private fun saveString(str: String?) {
        prefs.edit().putString(KEY_STRING, str).apply()
    }

    override fun onResume() {
        super.onResume()
        disposable.add(
            rxPreferences.getString(KEY_STRING).toObservable().subscribe(
                { updateText(it) },
                { Log.e(javaClass.name, "Error!", it) })
        )
    }

    private fun updateText(str: String?) {
        label.text = str
    }

    override fun onPause() {
        super.onPause()
        disposable.clear()
    }
}
