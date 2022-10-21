package nl.yoerivanhoek.rijksdemo.ui.model

sealed class ArtUiModel {
    class ArtItem(
        val id: String,
        val title: String,
        val author: String,
        val imageUrl: String
    ) : ArtUiModel()

    class AuthorSeparator(val author: String) : ArtUiModel()
}