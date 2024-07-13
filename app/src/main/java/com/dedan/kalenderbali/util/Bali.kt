package com.dedan.kalenderbali.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.roundToInt

val wukuNames = listOf(
    "Sinta", "Landep", "Ukir", "Kulantir", "Tolu", "Gumbreg", "Wariga",
    "Warigadean", "Julungwangi", "Sungsang", "Dungulan", "Kuningan",
    "Langkir", "Medangsia", "Pujut", "Pahang", "Krulut", "Merakih",
    "Tambir", "Medangkungan", "Matal", "Uye", "Menail", "Prangbakat",
    "Bala", "Ugu", "Wayang", "Kelawu", "Dukut", "Watugunung"
)

@RequiresApi(Build.VERSION_CODES.O)
val referenceDate: LocalDate = LocalDate.of(2023, 12, 19)

@RequiresApi(Build.VERSION_CODES.O)
fun weeksBetween(d1: LocalDate, d2: LocalDate): Int {
    val daysBetween = ChronoUnit.DAYS.between(d1, d2)
    return (daysBetween / 7.0).roundToInt()
}

@RequiresApi(Build.VERSION_CODES.O)
fun getWuku(current: LocalDate): String {
    val betweenWeek = weeksBetween(referenceDate, current)
    val resetDate = betweenWeek / 30
    val preDate = resetDate * 30
    val wukuIndex = betweenWeek - preDate
    return wukuNames[wukuIndex]
}
