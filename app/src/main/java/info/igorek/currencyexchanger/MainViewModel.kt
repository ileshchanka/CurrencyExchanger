package info.igorek.currencyexchanger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.igorek.currencyexchanger.repository.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
) : ViewModel() {

    fun getCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            apiRepository.getCurrencies()
        }
    }
}