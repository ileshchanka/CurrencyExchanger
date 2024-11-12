package info.igorek.currencyexchanger.domain.repository

import info.igorek.currencyexchanger.data.db.RatesDao
import info.igorek.currencyexchanger.domain.mapper.ExchangeRateResponseToUiMapper
import info.igorek.currencyexchanger.domain.mapper.ExchangeRateToBalanceMapper
import info.igorek.currencyexchanger.domain.model.ExchangeRate
import info.igorek.currencyexchanger.data.network.ApiService
import javax.inject.Inject

interface ApiRepository {
    suspend fun getCurrencies(): List<ExchangeRate>
}

class ApiRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val exchangeRateResponseToUiMapper: ExchangeRateResponseToUiMapper,
    private val exchangeRateToBalanceMapper: ExchangeRateToBalanceMapper,
    private val dao: RatesDao,
) : ApiRepository {

    override suspend fun getCurrencies(): List<ExchangeRate> {

        val rates = exchangeRateResponseToUiMapper.map(apiService.currencyExchangeRates())

        dao.insertCurrenciesIfNeed(
            rates.map(exchangeRateToBalanceMapper::map)
        )

        return rates
    }
}
