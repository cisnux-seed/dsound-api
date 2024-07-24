package site.dsound.api.infrastructures.dtos

data class AcrCloudHummingResponse(
    val acrid: String,
    val title: String,
    val score: Float,
    val artists: List<AcrArtistResponse>
)
