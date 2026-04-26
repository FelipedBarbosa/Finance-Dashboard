package me.felipebarbosa.finance.service;

import lombok.RequiredArgsConstructor;
import me.felipebarbosa.finance.dto.AssetRequestDTO;
import me.felipebarbosa.finance.dto.AssetResponseDTO;
import me.felipebarbosa.finance.mapper.AssetMapper;
import me.felipebarbosa.finance.repository.AssetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
