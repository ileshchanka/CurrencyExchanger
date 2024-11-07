package info.igorek.currencyexchanger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.igorek.currencyexchanger.db.CurrencyBalanceEntity
import info.igorek.currencyexchanger.model.ExchangeRate
import info.igorek.currencyexchanger.repository.ApiRepository
import info.igorek.currencyexchanger.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {

    private val _mainUiState = MutableStateFlow(MainUiState())
    val mainUiState: StateFlow<MainUiState> = _mainUiState

    data class MainUiState(
        val rates: List<ExchangeRate> = emptyList(),
        val balances: List<CurrencyBalanceEntity> = emptyList(),
    )

    init {
        getCurrencies()
        getBalances()
    }

    private fun getCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            _mainUiState.update {
                it.copy(
                    rates = apiRepository.getCurrencies()
                )
            }
        }
    }

    private fun getBalances() {
        viewModelScope.launch(Dispatchers.IO) {
            _mainUiState.update {
                it.copy(
                    balances = databaseRepository.getBalances()
                )
            }
        }
    }

    fun updateBalances(fromCode: String, toCode: String, sellAmount: Double, receiveAmount: Double, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = databaseRepository.updateBalances(fromCode, toCode, sellAmount, receiveAmount)
            onComplete(result)
        }
    }
}
