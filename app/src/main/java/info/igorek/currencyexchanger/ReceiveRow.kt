package info.igorek.currencyexchanger

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
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

    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
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
                text = stringResource(R.string.receive),
            )
        }

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = text,
                color = Color.Green,
                modifier = Modifier.weight(1f),
            )

            Dropdown(
                currencyList = currencyList,
                onCurrencyChange = onCurrencyChange,
            )
        }
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
        onCurrencyChange = {},
    )
}
