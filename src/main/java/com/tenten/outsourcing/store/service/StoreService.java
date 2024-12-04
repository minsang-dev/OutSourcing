package com.tenten.outsourcing.store.service;

import com.tenten.outsourcing.common.Auth;
import com.tenten.outsourcing.store.dto.StoreRequestDto;
import com.tenten.outsourcing.store.dto.StoreResponseDto;
import com.tenten.outsourcing.store.entity.Store;
import com.tenten.outsourcing.store.repository.StoreRepository;
import com.tenten.outsourcing.user.entity.User;
import com.tenten.outsourcing.user.repository.UserRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.sasl.AuthenticationException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Transactional
    public StoreResponseDto create(Long userId, StoreRequestDto requestDto) throws Exception {

        User findUser = userRepository.findById(userId).orElseThrow();

        List<Store> stores = storeRepository.findByUserId(userId);

        if (!Auth.OWNER.equals(findUser.getAuth())) {
        }

        if (!(stores.size() < 3)) {
        }

        Store store = storeRepository.save(new Store(requestDto, findUser));

        return new StoreResponseDto(store);

    }

}
