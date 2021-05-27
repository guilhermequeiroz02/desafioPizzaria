package com.example.desafiopizzaria

import java.text.DecimalFormat


fun Double.currencyFormat(): String {
    val format = DecimalFormat("R$ #,###.00");
    return format.format(this)

}
