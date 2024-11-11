package info.igorek.currencyexchanger

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import info.igorek.currencyexchanger.db.CurrencyBalanceEntity

@Composable
fun Dropdown(
    modifier: Modifier = Modifier,
    currencyList: List<CurrencyBalanceEntity>,
    onCurrencyChange: (CurrencyBalanceEntity) -> Unit,
) {
    val isDropDownExpanded = remember { mutableStateOf(false) }
    val itemPosition = remember { mutableIntStateOf(0) }

    if (currencyList.isNotEmpty()) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        isDropDownExpanded.value = true
                    }
                ) {
                    Text(text = currencyList[itemPosition.intValue].code)
                    Image(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                    )
                }

                DropdownMenu(
                    expanded = isDropDownExpanded.value,
                    onDismissRequest = {
                        isDropDownExpanded.value = false
                    }) {
                    currencyList.forEachIndexed { index, currency ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = currency.code,
                                    fontWeight = if (currency.balance > 0) FontWeight.Bold else FontWeight.Normal,
                                )
                            },
                            onClick = {
                                isDropDownExpanded.value = false
                                itemPosition.intValue = index
                                onCurrencyChange(currency)
                            }
                        )
                    }
                }
            }
        }
    } else {
        Text(text = stringResource(R.string.dropdown_bo_currencies))
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Dropdown(
        currencyList = listOf(
            CurrencyBalanceEntity("USD", 0.0),
            CurrencyBalanceEntity("EUR", 0.0),
            CurrencyBalanceEntity("GBP", 0.0),
        ),
        onCurrencyChange = {},
    )
}
