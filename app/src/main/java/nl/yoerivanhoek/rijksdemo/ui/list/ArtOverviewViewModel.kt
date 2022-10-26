package nl.yoerivanhoek.rijksdemo.ui.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import kotlinx.coroutines.flow.map
import nl.yoerivanhoek.rijksdemo.domain.usecase.GetArtItems
import nl.yoerivanhoek.rijksdemo.ui.model.ArtUiModel.ArtItem
import nl.yoerivanhoek.rijksdemo.ui.model.ArtUiModel.AuthorSeparator

class ArtOverviewViewModel(
    getArtItems: GetArtItems,
    private val artUiModelMapper: ArtUiModelMapper
) : ViewModel() {

    val artCollectionFlow = getArtItems().map { pagingData ->
        pagingData.map(artUiModelMapper::map)
            .insertSeparators { before, after ->
                if (after != null && hasDifferentAuthor(after, before)) {
                    AuthorSeparator(after.author)
                } else null
            }
    }.cachedIn(viewModelScope)

    private fun hasDifferentAuthor(after: ArtItem?, before: ArtItem?) =
        before?.author != after?.author

}