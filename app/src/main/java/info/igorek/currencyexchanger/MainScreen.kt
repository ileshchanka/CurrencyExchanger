package info.igorek.currencyexchanger

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import info.igorek.currencyexchanger.MainViewModel.MainUiState
import info.igorek.currencyexchanger.db.CurrencyBalanceEntity
import info.igorek.currencyexchanger.model.ExchangeRate
import info.igorek.currencyexchanger.ui.theme.HavelockBlue
import java.util.Locale

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val mainUiState by viewModel.mainUiState.collectAsStateWithLifecycle()

    var sellAmount by remember { mutableStateOf("100.00") }
    var sellCurrency by remember {
        mutableStateOf(
            // TODO Find better way
            mainUiState.balances.firstOrNull() ?: CurrencyBalanceEntity(
                "",
                0.0
            )
        )
    }
    var receiveCurrency by remember {
        mutableStateOf(
            // TODO Find better way
            mainUiState.balances.firstOrNull() ?: CurrencyBalanceEntity(
                "",
                0.0
            )
        )
    }

    val receiveAmount = calculateReceiveAmount(sellAmount, sellCurrency, receiveCurrency, mainUiState.rates)

    MainScreen(
        modifier = modifier,
        mainUiState = mainUiState,
        sellAmount = sellAmount,
        sellCurrency = sellCurrency,
        receiveAmount = receiveAmount,
        receiveCurrency = receiveCurrency,
        onSellAmountChange = { sellAmount = it },
        onSellCurrencyChange = { sellCurrency = it },
        onReceiveCurrencyChange = { receiveCurrency = it },
    )
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    mainUiState: MainUiState,
    sellAmount: String,
    sellCurrency: CurrencyBalanceEntity,
    receiveAmount: String,
    receiveCurrency: CurrencyBalanceEntity,
    onSellAmountChange: (String) -> Unit,
    onSellCurrencyChange: (CurrencyBalanceEntity) -> Unit,
    onReceiveCurrencyChange: (CurrencyBalanceEntity) -> Unit,
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            Text(
                text = "Currency converter",
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 1,
                textAlign = TextAlign.Center,
                color = Color.White,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .background(color = HavelockBlue)
                    .padding(all = 16.dp)
                    .fillMaxWidth(),
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Text(
                        text = "My Balances".uppercase(),
                        modifier = Modifier.padding(16.dp),
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = mainUiState.balances.joinToString(separator = "   ") {
                                "${it.balance} ${it.code}"
                            }
                        )
                    }

                    Text(
                        text = "Currency Exchange".uppercase(),
                        modifier = Modifier.padding(16.dp),
                    )

                    SellRow(
                        amount = sellAmount,
                        currencyList = mainUiState.balances,
                        onAmountChange = onSellAmountChange,
                        onCurrencyChange = onSellCurrencyChange,
                    )

                    HorizontalDivider(color = Color.Gray, thickness = 1.dp)

                    ReceiveRow(
                        text = receiveAmount,
                        currencyList = mainUiState.balances,
                        onCurrencyChange = onReceiveCurrencyChange,
                    )
                }

                Button(
                    onClick = { /* Handle submit action */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .imePadding()
                ) {
                    Text("Submit")
                }
            }
        }
    }
}

fun calculateReceiveAmount(
    sellAmount: String,
    sellCurrency: CurrencyBalanceEntity,
    receiveCurrency: CurrencyBalanceEntity,
    rates: List<ExchangeRate>
): String {
    val sellRate = rates.find { it.code == sellCurrency.code }?.rate ?: 1.0
    val receiveRate = rates.find { it.code == receiveCurrency.code }?.rate ?: 1.0
    val amount = sellAmount.toDoubleOrNull() ?: 0.0
    return String.format(Locale.ENGLISH, "%+.2f", amount * receiveRate / sellRate)
}

@Preview
@Composable
private fun Preview() {
    MainScreen(
        mainUiState = MainUiState(
            rates = listOf(
                ExchangeRate("USD", 1.0),
                ExchangeRate("EUR", 0.8),
                ExchangeRate("GBP", 0.7),
            ),
            balances = listOf(
                CurrencyBalanceEntity("USD", 100.0),
                CurrencyBalanceEntity("EUR", 200.0),
                CurrencyBalanceEntity("GBP", 300.0),
            )
        ),
        sellAmount = "100.00",
        sellCurrency = CurrencyBalanceEntity("USD", 100.0),
        receiveAmount = "80.00",
        receiveCurrency = CurrencyBalanceEntity("EUR", 200.0),
        onSellAmountChange = {},
        onSellCurrencyChange = {},
        onReceiveCurrencyChange = {},
    )
}
