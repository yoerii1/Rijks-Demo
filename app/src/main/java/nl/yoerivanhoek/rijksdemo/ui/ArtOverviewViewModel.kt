package nl.yoerivanhoek.rijksdemo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import kotlinx.coroutines.flow.map
import nl.yoerivanhoek.rijksdemo.domain.usecase.GetArtItems
import nl.yoerivanhoek.rijksdemo.ui.ArtOverviewViewModel.ArtUIModel.AuthorSeparator

class ArtOverviewViewModel(
    getArtItems: GetArtItems
) : ViewModel() {

    val artCollectionFlow = getArtItems().map { pagingData ->
        pagingData.map { art ->
            ArtUIModel.ArtData(
                id = art.objectNumber,
                title = art.title,
                author = art.author,
                imageUrl = art.imageUrl
            )
        }.insertSeparators { before, after ->
            if (after != null && before?.author != after.author) {
                AuthorSeparator(after.author)
            } else null
        }
    }.cachedIn(viewModelScope)

    sealed class ArtUIModel {
        class ArtData(
            val id: String,
            val title: String,
            val author: String,
            val imageUrl: String
        ) : ArtUIModel()

        class AuthorSeparator(val author: String) : ArtUIModel()
    }

}