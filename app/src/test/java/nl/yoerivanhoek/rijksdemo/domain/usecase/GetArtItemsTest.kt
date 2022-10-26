package nl.yoerivanhoek.rijksdemo.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import nl.yoerivanhoek.rijksdemo.domain.ArtItemRepository
import nl.yoerivanhoek.rijksdemo.domain.model.ArtItem
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class GetArtItemsTest {

    private val artItemsRepository: ArtItemRepository = mock()

    private val getArtItems = GetArtItems(artItemsRepository)

    @Test
    fun `When getting artItems, Then return flow from repo`() {
        // Given
        val expectedFlow: Flow<PagingData<ArtItem>> = mock()
        whenever(artItemsRepository.getCollection()).thenReturn(expectedFlow)

        // When
        val actualFlow = getArtItems()

        assertEquals(expectedFlow, actualFlow)
    }
}