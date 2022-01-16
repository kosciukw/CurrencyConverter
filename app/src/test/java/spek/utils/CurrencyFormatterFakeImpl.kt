package spek.utils

import com.kosciukvictor.currencyconverter.domain.utils.formatter.CurrencyFormatter

internal class CurrencyFormatterFakeImpl : CurrencyFormatter {
    override fun priceToString(price: Double): String {
        return price.toString()
    }
}