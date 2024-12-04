package com.tenten.outsourcing.service;

import com.tenten.outsourcing.dto.StoreRequestDto;
import com.tenten.outsourcing.dto.StoreResponseDto;
import com.tenten.outsourcing.repository.StoreRepository;
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
