package com.dedan.kalenderadat.model

import kotlinx.serialization.Serializable

@Serializable
data class PurtimDetail(
    val id: Int,
    val type: String,
    val date: String
)