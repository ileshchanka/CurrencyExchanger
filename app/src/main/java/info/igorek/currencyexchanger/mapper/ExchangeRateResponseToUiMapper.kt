package info.igorek.currencyexchanger.mapper

import info.igorek.currencyexchanger.model.ExchangeRate
import info.igorek.currencyexchanger.model.RatesResponse
import javax.inject.Inject

class ExchangeRateResponseToUiMapper @Inject constructor() {

    fun map(response: RatesResponse): List<ExchangeRate> {
        return response.rates.map { (code, rate) ->
            ExchangeRate(
                code = code,
                rate = rate,
            )
        }
    }
}
