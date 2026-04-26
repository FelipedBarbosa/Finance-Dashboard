package me.felipebarbosa.finance.service;

import lombok.RequiredArgsConstructor;
import me.felipebarbosa.finance.client.CoinGeckoClient;
import me.felipebarbosa.finance.model.Asset;
import me.felipebarbosa.finance.model.PriceHistory;
import me.felipebarbosa.finance.repository.AssetRepository;
import me.felipebarbosa.finance.repository.PriceHistoryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final CoinGeckoClient client;
    private final AssetRepository repository;
    private final PriceHistoryRepository historyRepository;

    public void updatePrice(Asset asset) {
        BigDecimal price = client.getPrice(asset.getSymbol());

        asset.setCurrentPrice(price);
        asset.setLastUpdated(LocalDateTime.now());

        repository.save(asset);

        // SALVAR HISTÓRICO
        PriceHistory history = PriceHistory.builder()
                .asset(asset)
                .price(price)
                .timestamp(LocalDateTime.now())
                .build();

        historyRepository.save(history);
    }

    public void updateAllPrices() {
        var assets = repository.findAll();

        for (Asset asset : assets) {
            updatePrice(asset);
        }
    }
}
