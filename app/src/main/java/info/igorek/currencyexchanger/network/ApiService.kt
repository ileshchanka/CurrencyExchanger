package info.igorek.currencyexchanger.network

import info.igorek.currencyexchanger.model.RatesResponse
import retrofit2.http.GET

interface ApiService {

    @GET("tasks/api/currency-exchange-rates")
    suspend fun currencyExchangeRates(): RatesResponse
}
