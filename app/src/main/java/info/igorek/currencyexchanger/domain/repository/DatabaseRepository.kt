package info.igorek.currencyexchanger.domain.repository

import info.igorek.currencyexchanger.data.db.CurrencyBalanceEntity
import info.igorek.currencyexchanger.data.db.RatesDao
import javax.inject.Inject

interface DatabaseRepository {
    suspend fun getBalances(): List<CurrencyBalanceEntity>
    suspend fun updateBalances(fromCode: String, toCode: String, sellAmount: Double, receiveAmount: Double): Boolean
}

class DatabaseRepositoryImpl @Inject constructor(
    private val dao: RatesDao,
) : DatabaseRepository {

    override suspend fun getBalances(): List<CurrencyBalanceEntity> {
        return dao.getAllSorted()
    }

    override suspend fun updateBalances(
        fromCode: String,
        toCode: String,
        sellAmount: Double,
        receiveAmount: Double,
    ): Boolean {
        val rowUpdated = dao.updateBalances(fromCode, toCode, sellAmount, receiveAmount)
        return rowUpdated == 2
    }
}
