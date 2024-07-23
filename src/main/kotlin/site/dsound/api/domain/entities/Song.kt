package site.dsound.api.domain.entities

import java.time.LocalDate

data class Song(
    val id: String,
    val title: String,
    val releaseDate: LocalDate,
    val albumName: String,
    val score: Float
)
