package info.igorek.currencyexchanger.repository

import info.igorek.currencyexchanger.db.CurrencyBalanceEntity
import info.igorek.currencyexchanger.db.RatesDao
import javax.inject.Inject

interface DatabaseRepository {
    suspend fun getBalances(): List<CurrencyBalanceEntity>
}

class DatabaseRepositoryImpl @Inject constructor(
    private val dao: RatesDao,
) : DatabaseRepository {

    override suspend fun getBalances(): List<CurrencyBalanceEntity> {
        return dao.getAllSorted()
    }
}
