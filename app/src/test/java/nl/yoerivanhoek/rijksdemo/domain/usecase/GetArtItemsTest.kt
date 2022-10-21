package nl.yoerivanhoek.rijksdemo.domain.usecase

import androidx.paging.PagingData
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import nl.yoerivanhoek.rijksdemo.domain.ArtItemRepository
import nl.yoerivanhoek.rijksdemo.domain.model.ArtItem
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GetArtItemsTest {

    private val artItemsRepository: ArtItemRepository = mockk()

    private val getArtItems = GetArtItems(artItemsRepository)

    @Test
    fun `When getting artItems, Then return flow from repo`() {
        // Given
        val expectedFlow: Flow<PagingData<ArtItem>> = mockk()
        every { artItemsRepository.getCollection() } returns expectedFlow

        // When
        val actualFlow = getArtItems()

        assertEquals(expectedFlow, actualFlow)
    }
}