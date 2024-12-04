package com.tenten.outsourcing.store.service;

import com.tenten.outsourcing.store.dto.StoreRequestDto;
import com.tenten.outsourcing.store.dto.StoreResponseDto;
import com.tenten.outsourcing.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

    public StoreResponseDto create(Long userId, StoreRequestDto requestDto) {
        return new StoreResponseDto(storeRepository.findById(userId).orElseThrow());


    }

}
