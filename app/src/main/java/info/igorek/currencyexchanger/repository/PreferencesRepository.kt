package info.igorek.currencyexchanger.repository

import android.content.SharedPreferences
import javax.inject.Inject

interface PreferencesRepository {

    fun saveExchangeCount(count: Int)
    val exchangeCount: Int
}

class PreferencesRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : PreferencesRepository {

    companion object {
        private const val PREF_EXCHANGE_COUNT = "PREF_EXCHANGE_COUNT"
    }


    override fun saveExchangeCount(count: Int) {
        sharedPreferences.edit().putInt(PREF_EXCHANGE_COUNT, count).apply()
    }

    override val exchangeCount: Int
        get() = sharedPreferences.getInt(PREF_EXCHANGE_COUNT, 0)
}
