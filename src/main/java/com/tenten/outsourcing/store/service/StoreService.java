package com.tenten.outsourcing.store.service;

import com.tenten.outsourcing.store.dto.StoreRequestDto;
import com.tenten.outsourcing.store.dto.StoreResponseDto;
import com.tenten.outsourcing.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;


    public StoreResponseDto create(Long userId, StoreRequestDto requestDto) {
        return new StoreResponseDto(storeRepository.findById(userId).orElseThrow());


    }

    public Page<StoreResponseDto> findByName(String name, Pageable pageable) {
        Page<StoreResponseDto> storeResponseDtoPage;
        storeResponseDtoPage = storeRepository.findByNameLike(name, pageable).map(StoreResponseDto::new);

        return storeResponseDtoPage;
    }
}
