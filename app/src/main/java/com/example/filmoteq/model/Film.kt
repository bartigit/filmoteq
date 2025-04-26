package com.example.filmoteq.model

import android.net.Uri
import java.time.LocalDate
import java.util.UUID

data class Film(
    val id: UUID = UUID.randomUUID(),
    var title: String,
    var releaseDate: LocalDate,
    var category: Category,
    var status: Status,
    var rating: Int? = null,
    var posterUri: Uri? = null
)