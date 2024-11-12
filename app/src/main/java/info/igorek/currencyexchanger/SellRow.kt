package info.igorek.currencyexchanger

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.igorek.currencyexchanger.db.CurrencyBalanceEntity

@Composable
fun SellRow(
    amount: String,
    commission: Double,
    currencyCode: String,
    currencyList: List<CurrencyBalanceEntity>,
    onAmountChange: (String) -> Unit,
    onCurrencyChange: (CurrencyBalanceEntity) -> Unit,
    hasCommission: Boolean,
    isAmountEnoughBalance: Boolean,
) {
    var textState by remember { mutableStateOf(TextFieldValue(amount)) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier.size(24.dp),
            )

            Text(
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge,
                text = stringResource(R.string.sell),
            )
        }

        Row(
            modifier = Modifier.padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextField(
                value = textState,
                onValueChange = { newValue ->
                    val filteredValue = newValue.text.filter { it.isDigit() || it == '.' }
                    textState = newValue.copy(text = filteredValue)
                    onAmountChange(filteredValue)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = if (isAmountEnoughBalance) Color.Black else Color.Red,
                ),
                modifier = Modifier.weight(1f),
            )

            Dropdown(
                currencyList = currencyList,
                onCurrencyChange = {
                    onCurrencyChange(it)
                },
            )
        }

        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            if (hasCommission) {
                Text(
                    text = stringResource(R.string.commission, commission, currencyCode),
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Red,
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    SellRow(
        amount = "100.00",
        commission = 0.7,
        currencyCode = "USD",
        currencyList = listOf(
            CurrencyBalanceEntity("USD", 100.0),
            CurrencyBalanceEntity("EUR", 200.0),
            CurrencyBalanceEntity("GBP", 300.0),
        ),
        onAmountChange = {},
        onCurrencyChange = {},
        hasCommission = true,
        isAmountEnoughBalance = true,
    )
}
