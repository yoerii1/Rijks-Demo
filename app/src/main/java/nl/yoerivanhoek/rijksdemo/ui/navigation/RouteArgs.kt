package nl.yoerivanhoek.rijksdemo.ui.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data object ArtListArgs : Parcelable

@Parcelize
@Serializable
data class ArtDetailsArgs(val artId: String) : Parcelable