package nl.yoerivanhoek.rijksdemo.ui

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import nl.yoerivanhoek.rijksdemo.TestCoroutineExtension
import nl.yoerivanhoek.rijksdemo.domain.model.ArtItem
import nl.yoerivanhoek.rijksdemo.domain.usecase.GetArtItems
import nl.yoerivanhoek.rijksdemo.ui.list.ArtOverviewViewModel
import nl.yoerivanhoek.rijksdemo.ui.list.ArtUiModelMapper
import nl.yoerivanhoek.rijksdemo.ui.model.ArtUiModel
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
internal class ArtOverviewViewModelTest {

    @JvmField
    @RegisterExtension
    val coroutines = TestCoroutineExtension()

    private val getArtItems: GetArtItems = mock()
    private val mapper = ArtUiModelMapper

    private val artItemsFlow = MutableStateFlow<PagingData<ArtItem>>(PagingData.empty())

    @BeforeEach
    fun setUp() {
        whenever(getArtItems()).thenReturn(artItemsFlow)
    }

    @Test
    fun `When artItems are available, Then authors are inserted as separators`() = runTest {
        // Given
        val artItems = listOf(
            artItem("authorA"),
            artItem("authorA"),
            artItem("authorB"),
            artItem("authorC"),
            artItem("authorD"),
        )

        // When
        val viewModel = ArtOverviewViewModel(getArtItems, mapper)
        artItemsFlow.value = PagingData.from(artItems)

        // Then
        val differ = AsyncPagingDataDiffer(
            diffCallback = MyDiffCallback(),
            updateCallback = NoopListCallback(),
            workerDispatcher = Dispatchers.Main
        )

        viewModel.artCollectionFlow.test {
            differ.submitData(awaitItem())
            assertTrue(differ.snapshot().items[0] is ArtUiModel.AuthorSeparator)
            assertTrue(differ.snapshot().items[1] is ArtUiModel.ArtItem)
            assertTrue(differ.snapshot().items[2] is ArtUiModel.ArtItem)
            assertTrue(differ.snapshot().items[3] is ArtUiModel.AuthorSeparator)
            assertTrue(differ.snapshot().items[4] is ArtUiModel.ArtItem)
            assertTrue(differ.snapshot().items[5] is ArtUiModel.AuthorSeparator)
            assertTrue(differ.snapshot().items[6] is ArtUiModel.ArtItem)
            assertTrue(differ.snapshot().items[7] is ArtUiModel.AuthorSeparator)
            assertTrue(differ.snapshot().items[8] is ArtUiModel.ArtItem)
        }
    }

    @Test
    fun `When items are available, Then the UiModel is correctly mapped`() {

    }

    private fun artItem(author: String) = ArtItem(
        "objectNumber", "title", author, "imageUrl"
    )

    class NoopListCallback : ListUpdateCallback {
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
    }

    class MyDiffCallback : DiffUtil.ItemCallback<ArtUiModel>() {
        override fun areItemsTheSame(oldItem: ArtUiModel, newItem: ArtUiModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ArtUiModel, newItem: ArtUiModel): Boolean {
            return oldItem == newItem
        }
    }
}