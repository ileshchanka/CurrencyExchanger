package info.igorek.currencyexchanger.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        CurrencyBalanceEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ratesDao(): RatesDao
}
