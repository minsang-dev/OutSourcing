package com.tenten.outsourcing.store.service;

import com.tenten.outsourcing.common.Auth;
import com.tenten.outsourcing.store.dto.StoreRequestDto;
import com.tenten.outsourcing.store.dto.StoreResponseDto;
import com.tenten.outsourcing.store.entity.Store;
import com.tenten.outsourcing.store.repository.StoreRepository;
import com.tenten.outsourcing.user.entity.User;
import com.tenten.outsourcing.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Transactional
    public StoreResponseDto create(Long userId, StoreRequestDto requestDto) {

        User findUser = userRepository.findById(userId).orElseThrow();
        List<Store> stores = storeRepository.findByUserId(userId);

        if (!Auth.OWNER.equals(findUser.getAuth())) {
        }

        if (!(stores.size() < 3)) {
        }

        Store store = storeRepository.save(new Store(requestDto, findUser));

        return new StoreResponseDto(store);

    }

    public List<StoreResponseDto> findByName(String name, Pageable pageable) {
        List<StoreResponseDto> storeResponseDtoPage;
        storeResponseDtoPage = storeRepository.findByNameLike(name, pageable).stream().map(StoreResponseDto::new).toList();

        return storeResponseDtoPage;
    }
}
