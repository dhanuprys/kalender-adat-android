package com.dedan.kalenderadat.util

fun <T> List<T>.safeSlice(range: IntRange): List<T> {
    val safeStart = range.first.coerceIn(0, size)
    val safeEnd = range.last.coerceIn(0, size - 1)
    return slice(safeStart..safeEnd)
}

fun String.safeSlice(range: IntRange): String {
    val safeStart = range.first.coerceIn(0, length)
    val safeEnd = range.last.coerceIn(0, length - 1)
    return substring(safeStart, safeEnd + 1)
}