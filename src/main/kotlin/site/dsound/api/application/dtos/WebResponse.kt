package site.dsound.api.application.dtos

data class WebResponse<out T>(val data: T, val meta: MetaResponse)
