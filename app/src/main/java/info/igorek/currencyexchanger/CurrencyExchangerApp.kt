package info.igorek.currencyexchanger

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.igorek.currencyexchanger.ui.theme.HavelockBlue

@Composable
fun CurrencyExchangerApp(modifier: Modifier = Modifier) {

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
            Column {
                Text(
                    text = "My Balances".uppercase(),
                    modifier = Modifier.padding(16.dp),
                )

                Text(
                    text = "Currency Exchange".uppercase(),
                    modifier = Modifier.padding(16.dp),
                )
            }
        }
    }
}

@Preview
@Composable
private fun Prev() {
    CurrencyExchangerApp()
}