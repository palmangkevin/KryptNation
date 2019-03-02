package com.example.kevinkombate.kryptnation.util

fun String.isValidEthereumAddress(): Boolean {
    val regex = "^0x[0-9a-f]{40}$"
    return true //TODO: this.matches(regex.toRegex())
}

fun String.toEther(): String {
    val converted = this.toDouble()/1000000000000000000
    return "%.2f".format(converted)
}