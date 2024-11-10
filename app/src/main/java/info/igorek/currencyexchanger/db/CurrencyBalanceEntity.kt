package info.igorek.currencyexchanger.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_balance")
data class CurrencyBalanceEntity(
    @PrimaryKey val code: String,
    val balance: Double,
) {
    companion object {
        fun empty() = CurrencyBalanceEntity("", 0.0)
    }
}