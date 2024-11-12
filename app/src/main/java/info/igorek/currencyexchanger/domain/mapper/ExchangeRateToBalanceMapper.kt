package info.igorek.currencyexchanger.domain.mapper

import info.igorek.currencyexchanger.data.db.CurrencyBalanceEntity
import info.igorek.currencyexchanger.domain.model.ExchangeRate
import javax.inject.Inject

class ExchangeRateToBalanceMapper @Inject constructor() {

    fun map(rate: ExchangeRate): CurrencyBalanceEntity {
        return CurrencyBalanceEntity(
            code = rate.code,
            balance = if (rate.code == "EUR") 1000.0 else 0.0,
        )
    }
}
