package uz.lazydevv.zbekztask.data.preferences

import android.content.SharedPreferences
import androidx.core.content.edit

class AppSharedPrefs(private val sharedPrefs: SharedPreferences) {

    var isPaid: Boolean = false
        get() = sharedPrefs.getBoolean(KEY_IS_PAID, false)
        set(value) {
            field = value
            sharedPrefs.edit { putBoolean(KEY_IS_PAID, value) }
        }

    companion object {

        private const val KEY_IS_PAID = "is_paid"
    }
}