package me.felipebarbosa.finance.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.felipebarbosa.finance.client.PriceClient;
import me.felipebarbosa.finance.model.Asset;
import me.felipebarbosa.finance.repository.AssetRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Business logic for assets.
 * <p>
 * Responsibilities:
 *   • CRUD-like retrieval of {@link Asset} entities.
 *   • Fetch latest market price from external providers via {@link PriceClient}.
 *   • Update the persisted price and timestamp.
 *   • Periodic batch update (every 5 minutes by default).
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AssetService {

    private final AssetRepository assetRepository;
    private final PriceClient priceClient;

    /**
     * Find all registered assets.
     */
    @Transactional(readOnly = true)
    public List<Asset> findAll() {
        return assetRepository.findAll();
    }

    /**
     * Find an asset by its ticker symbol.
     */
    @Transactional(readOnly = true)
    public Optional<Asset> findBySymbol(String symbol) {
        return assetRepository.findBySymbol(symbol.toUpperCase());
    }

    /**
     * Update the price of a single asset.
     *
     * @param symbol ticker symbol (case‑insensitive)
     * @return updated {@link Asset} or empty if the asset does not exist
     */
    @Transactional
    public Optional<Asset> updatePrice(String symbol) {
        String upper = symbol.toUpperCase();
        return assetRepository.findBySymbol(upper).map(asset -> {
            try {
                BigDecimal latest = priceClient.fetchPrice(upper);
                asset.setCurrentPrice(latest);
                asset.setLastUpdated(LocalDateTime.now());
                assetRepository.save(asset);
                log.info("Updated price for {}: {}", upper, latest);
            } catch (Exception e) {
                log.warn("Failed to fetch price for {}: {}", upper, e.getMessage());
            }
            return asset;
        });
    }

    /**
     * Scheduled batch update of all assets.
     * Runs every 5 minutes by default (configurable via cron expression).
     */
    @Scheduled(cron = "${price.update.cron:0 */5 * * * *}")
    public void updateAllPrices() {
        log.info("Starting batch price update for all assets");
        List<Asset> assets = assetRepository.findAll();
        for (Asset asset : assets) {
            try {
                BigDecimal latest = priceClient.fetchPrice(asset.getSymbol());
                asset.setCurrentPrice(latest);
                asset.setLastUpdated(LocalDateTime.now());
                assetRepository.save(asset);
                log.debug("Updated {} -> {}", asset.getSymbol(), latest);
            } catch (Exception e) {
                log.warn("Could not update {}: {}", asset.getSymbol(), e.getMessage());
            }
        }
        log.info("Batch price update completed");
    }
}
