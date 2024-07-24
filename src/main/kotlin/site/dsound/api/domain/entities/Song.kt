package site.dsound.api.domain.entities

data class Song(
    val id: String,
    val artistName: String,
    val title: String,
    val trackUri: String,
    val imageUrl: String? = null,
    val score: Float? = null
)
