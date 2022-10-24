package nl.yoerivanhoek.rijksdemo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nl.yoerivanhoek.rijksdemo.domain.usecase.GetArtDetails
import nl.yoerivanhoek.rijksdemo.ui.ArtDetailsViewModel.ArtDetailsState.Details
import nl.yoerivanhoek.rijksdemo.ui.ArtDetailsViewModel.ArtDetailsState.Error
import nl.yoerivanhoek.rijksdemo.ui.ArtDetailsViewModel.ArtDetailsState.Loading

class ArtDetailsViewModel(
    private val artId: String,
    private val getArtDetails: GetArtDetails
) : ViewModel() {

    private val _artDetailsState = MutableLiveData<ArtDetailsState>()
    val artDetailsState: LiveData<ArtDetailsState> = _artDetailsState

    init {
        loadArtDetails()
    }

    fun onRetry() {
        loadArtDetails()
    }

    private fun loadArtDetails() {
        _artDetailsState.value = Loading
        viewModelScope.launch {
            getArtDetails(artId = artId).onSuccess {
                _artDetailsState.value = Details(
                    it.objectNumber,
                    it.title,
                    it.description,
                    it.types,
                    it.author,
                    it.imageUrl,
                )
            }.onFailure {
                _artDetailsState.value = Error
            }
        }
    }

    sealed interface ArtDetailsState {
        object Loading : ArtDetailsState
        object Error : ArtDetailsState
        data class Details(
            val objectNumber: String,
            val title: String,
            val description: String?,
            val types: List<String>,
            val author: String,
            val imageUrl: String
        ) : ArtDetailsState
    }
}