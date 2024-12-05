package com.tenten.outsourcing.store.service;

import com.tenten.outsourcing.common.Auth;
import com.tenten.outsourcing.exception.InvalidInputException;
import com.tenten.outsourcing.exception.NoAuthorizedException;
import com.tenten.outsourcing.exception.NotFoundException;
import com.tenten.outsourcing.menu.entity.Menu;
import com.tenten.outsourcing.menu.repository.MenuRepository;
import com.tenten.outsourcing.store.dto.StoreDetailResponseDto;
import com.tenten.outsourcing.store.dto.StoreRequestDto;
import com.tenten.outsourcing.store.dto.StoreResponseDto;
import com.tenten.outsourcing.store.dto.StoreUpdateRequestDto;
import com.tenten.outsourcing.store.dto.StoreUpdateResponseDto;
import com.tenten.outsourcing.store.entity.Store;
import com.tenten.outsourcing.store.repository.StoreRepository;
import com.tenten.outsourcing.user.entity.User;
import com.tenten.outsourcing.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.tenten.outsourcing.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public StoreResponseDto create(Long userId, StoreRequestDto requestDto) {

        User findUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(NO_SESSION));
        List<Store> stores = storeRepository.findByUserId(userId);

        if (!Auth.OWNER.equals(findUser.getAuth())) {
            throw new NoAuthorizedException(NO_AUTHOR_USER);
        }

        if (!(stores.size() < 3)) {
            throw new InvalidInputException(STORE_REGISTRATION_LIMITED);
        }

        Store store = storeRepository.save(new Store(requestDto, findUser));

        return new StoreResponseDto(store);

    }

    public List<StoreResponseDto> findByName(String name, Pageable pageable) {
        List<StoreResponseDto> storeResponseDtoPage;
        storeResponseDtoPage = storeRepository.findByNameLike(name, pageable).stream().map(StoreResponseDto::new).toList();

        return storeResponseDtoPage;
    }

    @Transactional
    public StoreDetailResponseDto findById(Long storeId) {
        Store findStore = storeRepository.findById(storeId).orElseThrow(() -> new NotFoundException(NOT_FOUND_STORE));

        List<Menu> allMenu = menuRepository.findAllMenuByStoreId(storeId);

        return new StoreDetailResponseDto(findStore, allMenu);
    }

    @Transactional
    public StoreUpdateResponseDto updateById(Long userId, Long storeId, StoreUpdateRequestDto requestDto) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(NO_SESSION));

        if (!Auth.OWNER.equals(findUser.getAuth())) {
            throw new NoAuthorizedException(NO_AUTHOR_USER);
        }

        List<Store> ownStores = storeRepository.findByUserId(userId);

        Store findStore = storeRepository.findById(storeId).orElseThrow(() -> new NotFoundException(NOT_FOUND_STORE));

        List<Store> matchedStore = ownStores.stream().filter(store -> store.getId().equals(findStore.getId())).toList();

        if (!matchedStore.isEmpty()) {
            matchedStore.get(0).updateStoreInformation(requestDto);

        } else {
            throw new NoAuthorizedException(NOT_FOUND_STORE);
        }

        return new StoreUpdateResponseDto(matchedStore.get(0));
    }
}
