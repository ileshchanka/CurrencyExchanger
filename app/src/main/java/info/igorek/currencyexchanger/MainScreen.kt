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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
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

    var sellAmount by remember { mutableDoubleStateOf(100.00) }
    var commission by remember { mutableDoubleStateOf((sellAmount * COMMISSION_PERCENT).roundToDecimals(2)) }

    var sellCurrency by remember {
        mutableStateOf(mainUiState.balances.firstOrNull() ?: CurrencyBalanceEntity.empty())
    }

    var receiveCurrency by remember {
        mutableStateOf(mainUiState.balances.firstOrNull() ?: CurrencyBalanceEntity.empty())
    }

    LaunchedEffect(mainUiState.balances) {
        if (mainUiState.balances.isNotEmpty()) {
            sellCurrency = mainUiState.balances.first()
            receiveCurrency = mainUiState.balances.first()
        }
    }


    val exchangeCount = viewModel.getExchangeCount()

    val hasCommission by remember { derivedStateOf { exchangeCount >= 5 } }

    val receiveAmount =
        calculateReceiveAmount(sellAmount, sellCurrency, receiveCurrency, mainUiState.rates, hasCommission)

    val isAmountEnoughBalance by remember {
        derivedStateOf {
            val amountWithCommission = if (hasCommission) sellAmount + commission else sellAmount
            amountWithCommission <= sellCurrency.balance
        }
    }

    val isCurrenciesDifferent by remember { derivedStateOf { sellCurrency.code != receiveCurrency.code } }

    val isAmountNotEmpty by remember { derivedStateOf { sellAmount > 0.0 } }

    MainScreen(
        modifier = modifier,
        mainUiState = mainUiState,
        sellAmount = sellAmount,
        commission = commission,
        sellCurrency = sellCurrency,
        receiveAmount = receiveAmount,
        receiveCurrency = receiveCurrency,
        onSellAmountChange = {
            sellAmount = if (it.isNotEmpty()) it.toDouble() else 0.0
            commission = if (it.isNotEmpty()) (it.toDouble() * COMMISSION_PERCENT).roundToDecimals(2) else 0.0
        },
        onSellCurrencyChange = { sellCurrency = it },
        onReceiveCurrencyChange = { receiveCurrency = it },
        onSubmit = { onComplete ->
            viewModel.updateBalances(
                fromCode = sellCurrency.code,
                toCode = receiveCurrency.code,
                sellAmount = sellAmount + commission,
                receiveAmount = receiveAmount.toDouble(),
                onComplete = onComplete,
            )
        },
        hasCommission = hasCommission,
        isSubmitButtonEnabled = isAmountEnoughBalance && isCurrenciesDifferent && isAmountNotEmpty,
        isAmountEnoughBalance = isAmountEnoughBalance,
    )
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    mainUiState: MainUiState,
    sellAmount: Double,
    commission: Double,
    sellCurrency: CurrencyBalanceEntity,
    receiveAmount: String,
    receiveCurrency: CurrencyBalanceEntity,
    onSellAmountChange: (String) -> Unit,
    onSellCurrencyChange: (CurrencyBalanceEntity) -> Unit,
    onReceiveCurrencyChange: (CurrencyBalanceEntity) -> Unit,
    onSubmit: ((Boolean) -> Unit) -> Unit,
    hasCommission: Boolean,
    isSubmitButtonEnabled: Boolean,
    isAmountEnoughBalance: Boolean,
) {

    var showDialog by remember { mutableStateOf(false) }

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
                        amount = sellAmount.toString(),
                        commission = commission,
                        currencyCode = sellCurrency.code,
                        currencyList = mainUiState.balances,
                        onAmountChange = onSellAmountChange,
                        onCurrencyChange = onSellCurrencyChange,
                        hasCommission = hasCommission,
                        isAmountEnoughBalance = isAmountEnoughBalance,
                    )

                    HorizontalDivider(color = Color.Gray, thickness = 1.dp)

                    ReceiveRow(
                        text = receiveAmount,
                        currencyList = mainUiState.balances,
                        onCurrencyChange = onReceiveCurrencyChange,
                    )
                }

                Button(
                    onClick = {
                        onSubmit(
                            { success ->
                                if (success) {
                                    showDialog = true
                                }
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .imePadding(),
                    enabled = isSubmitButtonEnabled,
                ) {
                    Text("Submit")
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Done")
                }
            },
            text = {
                val commissionText = if (hasCommission) ". Commission Fee - $commission ${sellCurrency.code}." else ""
                Text("You have converted $sellAmount ${sellCurrency.code} to $receiveAmount ${receiveCurrency.code}$commissionText")
            }
        )
    }
}

fun calculateReceiveAmount(
    sellAmount: Double,
    sellCurrency: CurrencyBalanceEntity,
    receiveCurrency: CurrencyBalanceEntity,
    rates: List<ExchangeRate>,
    hasCommission: Boolean,
): String {
    val sellRate = rates.find { it.code == sellCurrency.code }?.rate ?: 1.0
    val receiveRate = rates.find { it.code == receiveCurrency.code }?.rate ?: 1.0
    val commission = if (hasCommission) COMMISSION_PERCENT else 0.0
    val amountAfterCommission = sellAmount * (1 - commission)
    return String.format(Locale.ENGLISH, "%+.2f", amountAfterCommission * receiveRate / sellRate)
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
        sellAmount = 100.00,
        commission = 0.7,
        sellCurrency = CurrencyBalanceEntity("USD", 100.0),
        receiveAmount = "80.00",
        receiveCurrency = CurrencyBalanceEntity("EUR", 200.0),
        onSellAmountChange = {},
        onSellCurrencyChange = {},
        onReceiveCurrencyChange = {},
        onSubmit = {},
        hasCommission = true,
        isSubmitButtonEnabled = true,
        isAmountEnoughBalance = true,
    )
}
