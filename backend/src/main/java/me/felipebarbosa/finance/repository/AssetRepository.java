package me.felipebarbosa.finance.repository;

import me.felipebarbosa.finance.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for {@link Asset} entities.
 */
@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    /**
     * Find an asset by its ticker symbol.
     *
     * @param symbol ticker symbol, e.g., "AAPL" or "BTC"
     * @return optional containing the asset if found
     */
    Optional<Asset> findBySymbol(String symbol);
}
  