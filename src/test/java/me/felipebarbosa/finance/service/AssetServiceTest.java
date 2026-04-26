package me.felipebarbosa.finance.service;

import me.felipebarbosa.finance.model.Asset;
import me.felipebarbosa.finance.repository.AssetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class AssetServiceTest {

    @Mock
    private AssetRepository assetRepository;

    @InjectMocks
    private AssetService assetService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll_returnsAllAssets() {
        Asset a = new Asset(1L, "AAPL", "Apple Inc.", BigDecimal.valueOf(150), LocalDateTime.now());
        when(assetRepository.findAll()).thenReturn(Collections.singletonList(a));

        var result = assetService.findAll();

        assertEquals(1, result.size());
        assertEquals("AAPL", result.get(0).getSymbol());
        verify(assetRepository).findAll();
    }

    @Test
    void testFindBySymbol_found() {
        Asset a = new Asset(2L, "MSFT", "Microsoft Corp.", BigDecimal.valueOf(300), LocalDateTime.now());
        when(assetRepository.findBySymbol("MSFT")).thenReturn(Optional.of(a));

        var opt = assetService.findBySymbol("msft"); // case-insensitive

        assertTrue(opt.isPresent());
        assertEquals("MSFT", opt.get().getSymbol());
        verify(assetRepository).findBySymbol("MSFT");
    }

    @Test
    void testFindBySymbol_notFound() {
        when(assetRepository.findBySymbol("XYZ")).thenReturn(Optional.empty());

        var opt = assetService.findBySymbol("xyz");

        assertTrue(opt.isEmpty());
        verify(assetRepository).findBySymbol("XYZ");
    }
}
