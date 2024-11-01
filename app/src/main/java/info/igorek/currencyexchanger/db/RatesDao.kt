package info.igorek.currencyexchanger.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RatesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCurrenciesIfNeed(balanceList: List<CurrencyBalanceEntity>)

    @Query("SELECT * FROM currency_balance")
    fun getAll(): List<CurrencyBalanceEntity>

    @Query("UPDATE currency_balance SET balance = balance - :amount WHERE code = :fromCode")
    fun decreaseBalance(fromCode: String, amount: Double)

    @Query("UPDATE currency_balance SET balance = balance + :amount WHERE code = :toCode")
    fun increaseBalance(toCode: String, amount: Double)
}
