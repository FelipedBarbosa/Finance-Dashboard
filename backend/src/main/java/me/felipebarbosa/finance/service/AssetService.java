package me.felipebarbosa.finance.service;

import lombok.RequiredArgsConstructor;
import me.felipebarbosa.finance.analysis.FinancialMetricsService;
import me.felipebarbosa.finance.dto.AssetMetricsDTO;
import me.felipebarbosa.finance.dto.AssetRequestDTO;
import me.felipebarbosa.finance.dto.AssetResponseDTO;
import me.felipebarbosa.finance.mapper.AssetMapper;
import me.felipebarbosa.finance.repository.AssetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Business logic for asset CRUD operations.
 */
@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository repository;
    private final FinancialMetricsService metricsService;

    public AssetResponseDTO create(AssetRequestDTO dto) {
        var asset = AssetMapper.toEntity(dto);
        var saved = repository.save(asset);
        return AssetMapper.toDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<AssetResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(AssetMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<AssetResponseDTO> findBySymbol(String symbol) {
        return repository.findBySymbol(symbol.toUpperCase())
                .map(AssetMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public Optional<AssetMetricsDTO> findMetricsById(Long id) {
        return repository.findById(id)
                .map(asset -> {
                    BigDecimal oldestPrice = metricsService.getOldestPrice(asset.getId());

                    BigDecimal percentageChange = metricsService.calculatePercentageChange(
                            oldestPrice,
                            asset.getCurrentPrice());

                    BigDecimal roi = metricsService.calculateROI(
                            oldestPrice,
                            asset.getCurrentPrice());

                    return AssetMetricsDTO.builder()
                            .symbol(asset.getSymbol())
                            .currentPrice(asset.getCurrentPrice())
                            .percentageChange(percentageChange.setScale(2, RoundingMode.HALF_UP))
                            .roi(roi.setScale(2, RoundingMode.HALF_UP))
                            .build();
                });
    }
}
