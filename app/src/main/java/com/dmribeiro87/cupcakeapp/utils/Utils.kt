package com.dmribeiro87.cupcakeapp.utils

import com.google.firebase.installations.Utils

fun twoDecimals(value: Double): String {
    return String.format("%.2f", value).replace(".", ",")
}

fun formatCardExpiryDate(date: String): String {
    val cleanDate = date.replace(Regex("[^\\d]"), "")
    return if (cleanDate.length >= 2) {
        val month = cleanDate.substring(0, 2)
        val year = if (cleanDate.length > 2) cleanDate.substring(2) else ""
        if (year.isNotEmpty()) "$month/$year" else month
    } else {
        cleanDate
    }
}

fun formatCreditCardNumber(cardNumber: String): String {
    val cleaned = cardNumber.replace("\\s".toRegex(), "")
    val regex = Regex("(.{1,4})")
    return cleaned.replace(regex, "$1 ").trim()
}

private fun process(mask: String, text: String): String{
    val number = onlyNumbers(text.trim()).toCharArray()
    var textMask = ""
    var numberIndex = 0
    for (maskItem in mask) {
        if (maskItem != '#') {
            textMask += maskItem
        } else if(numberIndex < number.size) {
            textMask += number[numberIndex]
            numberIndex++
        } else {
            break
        }
    }
    return textMask
}

fun onlyNumbers(numberWithMask: String): String {
    return Regex("[^0-9]").replace(numberWithMask, "")
}