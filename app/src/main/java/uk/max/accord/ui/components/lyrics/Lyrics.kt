package uk.max.accord.ui.components.lyrics

@JvmInline
value class Lyrics(val lyrics: List<LyricsLine>) {
    companion object {
        val Empty = Lyrics(emptyList())
    }
}

data class LyricsLine(
    val timestamp: Long,
    val agent: String?,
    val text: String,
    val background: String?
) {
    val isMain: Boolean
        get() = agent == null ||
                agent == "v1" ||
                agent == "1" ||
                agent == "M"
}