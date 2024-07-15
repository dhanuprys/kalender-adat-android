package com.dedan.kalenderadat.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DateEvent(
    val date: String,

    val colors: List<String>,

    @SerialName("event_count")
    val eventCount: Int
)
