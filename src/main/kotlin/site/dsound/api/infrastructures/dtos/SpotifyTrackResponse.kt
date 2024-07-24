package site.dsound.api.infrastructures.dtos

data class SpotifyTrackResponse(
    val limit: Int,
    val offset: Int,
    val total: Int,
    val items: List<TrackItemResponse>
)
