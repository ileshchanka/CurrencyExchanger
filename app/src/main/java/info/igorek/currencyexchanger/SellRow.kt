package info.igorek.currencyexchanger

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.igorek.currencyexchanger.db.CurrencyBalanceEntity

@Composable
fun SellRow(
    amount: String,
    currencyList: List<CurrencyBalanceEntity>,
    onAmountChange: (String) -> Unit,
    onCurrencyChange: (CurrencyBalanceEntity) -> Unit,
) {
    var textState by remember { mutableStateOf(TextFieldValue(amount)) }
    var selectedCurrency by remember { mutableStateOf(currencyList.firstOrNull()) }

    val isAmountExceedingBalance by remember {
        derivedStateOf {
            selectedCurrency?.let {
                (textState.text.toDoubleOrNull() ?: 0.0) > it.balance
            } ?: false
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Icon(
            imageVector = Icons.Default.KeyboardArrowUp,
            contentDescription = null,
            tint = Color.Red,
            modifier = Modifier.size(24.dp)
        )

        Text(
            modifier = Modifier.weight(1f),
            text = "Sell",
        )

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
                focusedTextColor = if (isAmountExceedingBalance) Color.Red else Color.Black,
            ),
            modifier = Modifier.width(150.dp), // TODO Find a better way to set width
        )

        Spacer(modifier = Modifier.width(4.dp))

        Dropdown(
            modifier = Modifier,
            currencyList = currencyList,
            onCurrencyChange = {
                selectedCurrency = it
                onCurrencyChange(it)
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    SellRow(
        amount = "100.00",
        currencyList = listOf(
            CurrencyBalanceEntity("USD", 100.0),
            CurrencyBalanceEntity("EUR", 200.0),
            CurrencyBalanceEntity("GBP", 300.0),
        ),
        onAmountChange = {},
        onCurrencyChange = {},
    )
}
