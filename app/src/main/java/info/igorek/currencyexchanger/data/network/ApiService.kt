package info.igorek.currencyexchanger.data.network

import info.igorek.currencyexchanger.domain.model.RatesResponse
import retrofit2.http.GET

interface ApiService {

    @GET("tasks/api/currency-exchange-rates")
    suspend fun currencyExchangeRates(): RatesResponse
}
