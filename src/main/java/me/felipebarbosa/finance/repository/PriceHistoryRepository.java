package me.felipebarbosa.finance.repository;

import me.felipebarbosa.finance.model.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Long> {

    List<PriceHistory> findByAssetIdOrderByTimestampAsc(Long assetId);
}