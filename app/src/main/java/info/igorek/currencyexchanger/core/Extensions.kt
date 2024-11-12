package info.igorek.currencyexchanger.core

import kotlin.math.pow

fun Double.roundToDecimals(decimals: Int): Double {
    val factor = 10.0.pow(decimals)
    return (this * factor).toInt() / factor
}
