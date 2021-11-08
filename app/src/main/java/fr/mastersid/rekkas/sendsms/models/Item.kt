package fr.mastersid.rekkas.sendsms.models

import com.squareup.moshi.Json

data class Item(
    @field: Json(name = "answer_count") val answer_count: Int,
    @field: Json(name = "title") val title: String,
)