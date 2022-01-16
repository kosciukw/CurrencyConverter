package com.kosciukvictor.currencyconverter.domain.utils.formatter

import java.text.DecimalFormat

class CurrencyFormatterImpl : CurrencyFormatter {

    override fun priceToString(price: Double): String {
        val toShow = priceWithoutDecimal(price)
        return if (toShow.indexOf(".") > 0) {
            priceWithDecimal(price)
        } else {
            priceWithoutDecimal(price)
        }
    }

    private fun priceWithDecimal(price: Double): String {
        val formatter =
            DecimalFormat("###,###,###.00")
        return formatter.format(price)
    }

    private fun priceWithoutDecimal(price: Double): String {
        val formatter = DecimalFormat("###,###,###.##")
        return formatter.format(price)
    }
}