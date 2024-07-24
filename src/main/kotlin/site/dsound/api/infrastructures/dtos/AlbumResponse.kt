package site.dsound.api.infrastructures.dtos

data class AlbumResponse(
    val id: String,
    val name: String,
    val uri: String,
    val images: List<ImageAlbumResponse>
)
