package info.igorek.currencyexchanger

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.igorek.currencyexchanger.db.CurrencyBalanceEntity

@Composable
fun ReceiveRow(
    modifier: Modifier = Modifier,
    text: String,
    currencyList: List<CurrencyBalanceEntity>,
    onCurrencyChange: (CurrencyBalanceEntity) -> Unit,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            tint = Color.Green,
            modifier = Modifier.size(24.dp)
        )

        Text(
            modifier = Modifier.weight(1f),
            text = "Receive",
        )

        Text(
            text = text,
            color = Color.Green,
            modifier = Modifier.width(150.dp), // TODO Find a better way to set width
        )

        Spacer(modifier = Modifier.width(4.dp))

        Dropdown(
            modifier = Modifier,
            currencyList = currencyList,
            onCurrencyChange = onCurrencyChange,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ReceiveRow(
        text = "100.00",
        currencyList = listOf(
            CurrencyBalanceEntity("USD", 100.0),
            CurrencyBalanceEntity("EUR", 200.0),
            CurrencyBalanceEntity("GBP", 300.0),
        ),
        onCurrencyChange = { },
    )
}