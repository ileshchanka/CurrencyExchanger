package info.igorek.currencyexchanger.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RatesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCurrenciesIfNeed(balanceList: List<CurrencyBalanceEntity>)

    @Query("SELECT * FROM currency_balance ORDER BY balance DESC, code ASC")
    fun getAllSorted(): List<CurrencyBalanceEntity>

    @Query(
        """UPDATE currency_balance
    SET balance = CASE
        WHEN code = :fromCode THEN balance - :sellAmount
        WHEN code = :toCode THEN balance + :receiveAmount
    END
    WHERE code IN (:fromCode, :toCode)"""
    )
    fun updateBalances(fromCode: String, toCode: String, sellAmount: Double, receiveAmount: Double): Int
}
