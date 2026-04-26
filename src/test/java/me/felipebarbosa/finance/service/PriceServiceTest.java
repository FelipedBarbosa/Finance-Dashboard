package me.felipebarbosa.finance.service;

import me.felipebarbosa.finance.client.CoinGeckoClient;
import me.felipebarbosa.finance.model.Asset;
import me.felipebarbosa.finance.repository.AssetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class PriceServiceTest {

    @Mock
    private CoinGeckoClient client;

    @Mock
    private AssetRepository repository;

    @InjectMocks
    private PriceService priceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdatePrice_savesUpdatedAsset() {
        Asset asset = new Asset(1L, "BTC", "Bitcoin", BigDecimal.valueOf(30000), LocalDateTime.now().minusHours(1));
        when(client.getPrice("BTC")).thenReturn(BigDecimal.valueOf(32000));
        when(repository.save(any(Asset.class))).thenAnswer(invocation -> invocation.getArgument(0));

        priceService.updatePrice(asset);

        ArgumentCaptor<Asset> captor = ArgumentCaptor.forClass(Asset.class);
        verify(repository).save(captor.capture());
        Asset saved = captor.getValue();

        assertEquals(BigDecimal.valueOf(32000), saved.getCurrentPrice());
        assertNotNull(saved.getLastUpdated());
        assertTrue(saved.getLastUpdated().isAfter(asset.getLastUpdated()));
    }
}
