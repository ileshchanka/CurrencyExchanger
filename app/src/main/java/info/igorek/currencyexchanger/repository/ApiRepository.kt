package info.igorek.currencyexchanger.repository

import info.igorek.currencyexchanger.mapper.ExchangeRateResponseToUiMapper
import info.igorek.currencyexchanger.model.ExchangeRate
import info.igorek.currencyexchanger.network.ApiService
import javax.inject.Inject

interface ApiRepository {
    suspend fun getCurrencies(): List<ExchangeRate>
}

class ApiRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val exchangeRateResponseToUiMapper: ExchangeRateResponseToUiMapper,
//    private val dao: RatesDao,
) : ApiRepository {

    override suspend fun getCurrencies(): List<ExchangeRate> {

        return exchangeRateResponseToUiMapper.map(apiService.currencyExchangeRates())

//        val table = tableRemoteToUiMapper.map(
//            apiService.currencyExchangeRates(),
//        )

//        dao.insertAll(table.rates)
    }
}
