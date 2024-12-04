package com.tenten.outsourcing.store.service;

import com.tenten.outsourcing.store.dto.StoreRequestDto;
import com.tenten.outsourcing.store.dto.StoreResponseDto;
import com.tenten.outsourcing.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;


    public StoreResponseDto create(Long userId, StoreRequestDto requestDto) {
        return new StoreResponseDto(storeRepository.findById(userId).orElseThrow());


    }

    public List<StoreResponseDto> findByName(String name, Pageable pageable) {
        List<StoreResponseDto> storeResponseDtoPage;
        storeResponseDtoPage = storeRepository.findByNameLike(name, pageable).stream().map(StoreResponseDto::new).toList();

        return storeResponseDtoPage;
    }
}
