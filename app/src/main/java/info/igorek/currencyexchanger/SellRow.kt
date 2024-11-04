package info.igorek.currencyexchanger

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
) {
    var textState by remember { mutableStateOf(TextFieldValue(amount)) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
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
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            modifier = Modifier.width(100.dp), // TODO Find a better way to set width
        )

        Spacer(modifier = Modifier.width(4.dp))

        Dropdown(
            modifier = Modifier,
            currencyList = currencyList,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    SellRow(
        amount = "100.00",
        currencyList = emptyList()
    )
}
