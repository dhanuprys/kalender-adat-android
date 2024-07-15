package com.dedan.kalenderadat.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventDetail(
    val id: Int,

    @SerialName("image_url")
    val imageUrl: String?,

    val title: String,

    val description: String?,

    @SerialName("category_color")
    val categoryColor: String,

    @SerialName("category_name")
    val categoryName: String,

    @SerialName("updated_at")
    val updatedAt: String
)
