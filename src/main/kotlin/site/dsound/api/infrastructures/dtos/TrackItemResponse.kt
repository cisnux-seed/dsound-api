package site.dsound.api.infrastructures.dtos

data class TrackItemResponse(
    val id: String,
    val name: String,
    val uri: String,
    val album: AlbumResponse,
    val artists: List<ArtistResponse>
)
