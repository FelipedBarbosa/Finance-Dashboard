package me.felipebarbosa.finance.controller;

import lombok.RequiredArgsConstructor;
import me.felipebarbosa.finance.dto.AssetRequestDTO;
import me.felipebarbosa.finance.dto.AssetResponseDTO;
import me.felipebarbosa.finance.model.PriceHistory;
import me.felipebarbosa.finance.repository.PriceHistoryRepository;
import me.felipebarbosa.finance.service.AssetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService service;

    @PostMapping
    public AssetResponseDTO create(@RequestBody AssetRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<AssetResponseDTO> findAll() {
        return service.findAll();
    }
}
