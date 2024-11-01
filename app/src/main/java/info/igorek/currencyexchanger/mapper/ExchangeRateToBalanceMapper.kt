package info.igorek.currencyexchanger.mapper

import info.igorek.currencyexchanger.db.CurrencyBalanceEntity
import info.igorek.currencyexchanger.model.ExchangeRate
import javax.inject.Inject

class ExchangeRateToBalanceMapper @Inject constructor() {

    fun map(rate: ExchangeRate): CurrencyBalanceEntity {
        return CurrencyBalanceEntity(
            code = rate.code,
            balance = if (rate.code == "EUR") 1000.0 else 0.0,
        )
    }
}
