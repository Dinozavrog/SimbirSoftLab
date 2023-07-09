package com.example.simbirsoft.presentation.utils

import java.time.Month

fun getMonthNumber(monthName: String): String {
    val monthNumber = Month.valueOf(monthName.toUpperCase()).value
    return String.format("%02d", monthNumber)
}
