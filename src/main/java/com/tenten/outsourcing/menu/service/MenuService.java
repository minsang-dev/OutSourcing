package com.tenten.outsourcing.menu.service;

import com.tenten.outsourcing.exception.ErrorCode;
import com.tenten.outsourcing.exception.NotFoundException;
import com.tenten.outsourcing.menu.dto.MenuResponseDto;
import com.tenten.outsourcing.menu.dto.MenuUpdateResponseDto;
import com.tenten.outsourcing.menu.entity.Menu;
import com.tenten.outsourcing.menu.repository.MenuRepository;
import com.tenten.outsourcing.store.entity.Store;
import com.tenten.outsourcing.store.repository.StoreRepository;
import com.tenten.outsourcing.user.entity.User;
import com.tenten.outsourcing.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final UserService userService ;

    // 메뉴 생성
    @Transactional
    public MenuResponseDto createMenu(
            HttpServletRequest request, Long storeId, String menuName, String menuPictureUrl, Integer price) {

        // 유저를 찾고, 가게를 찾고 -> 가게의 유저(사장)와 로그인한 유저를 비교 -> 맞으면 찾은 Store로 Menu 생성
        Store findStore = checkOwner(request, storeId);

        Menu savedMenu = menuRepository.save(new Menu(menuName, menuPictureUrl, price, findStore));

        return new MenuResponseDto(
                savedMenu.getId(),
                findStore.getId(),
                savedMenu.getMenuName(),
                savedMenu.getMenuPictureUrl(),
                savedMenu.getPrice(),
                savedMenu.getCreatedAt()
        );
    }

    // 메뉴 다건 조회
    @Transactional
    public List<Menu> getMenusByStoreId(Long storeId) {
        return menuRepository.findAllMenuByStoreId(storeId);
    }

    // 메뉴 수정 로직
    @Transactional
    public MenuUpdateResponseDto updateMenu(
            HttpServletRequest request, Long storeId, Long menuId, String menuName, String menuPictureUrl, Integer price) {

       checkOwner(request, storeId);

        Menu menu = findByIdOrElseThrow(menuId);

        menu.updateMenu(menuName, menuPictureUrl, price);
        Menu updatedMenu = menuRepository.save(menu);

        return new MenuUpdateResponseDto(
                storeId,
                updatedMenu.getId(),
                updatedMenu.getMenuName(),
                updatedMenu.getMenuPictureUrl(),
                updatedMenu.getPrice(),
                updatedMenu.getUpdatedAt()
        );

    }

    // 메뉴 삭제
    public void deleteMenu(HttpServletRequest request, Long storeId, Long menuId, String password) {

        checkOwner(request, storeId);

        // 1. 로그인한 User 찾기
        Long userId = userService.getSession(request).getId();
        User findUser = userService.findByIdOrElseThrow(userId);

        // 2. findUser의 패스워드와 비교
        userService.checkPasswordMatch(findUser.getPassword(), password);

        Menu findMenu = menuRepository.
                findById(menuId).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "메뉴를 찾을 수 없습니다."));

        // 위의 검증 통과 시 삭제일 추가
        findMenu.deleteMenu();
    }

    /**
     * 로그인한 유저가 가게 사장인지 체크
     * 가게의 주인인지 체크하는 공통 메서드, user 반환, store 반환, owner 체크
     * => (로그인한 유저 == owner)? store : exception
     */

    private Store checkOwner(HttpServletRequest request, Long storeId) {

        Long ownerId = userService.getSession(request).getId();

        User findUser = userService.findByIdOrElseThrow(ownerId);
        Store findStore = storeRepository.findById(storeId).orElseThrow(()
                -> new IllegalArgumentException("가게를 찾을 수 없습니다."));

        Long findUserId = findStore.getUser().getId();
        if (!findUserId.equals(findUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return findStore;
    }

    // 단건 조회
    public Menu findByIdOrElseThrow(Long menuId) {
        return menuRepository.findById(menuId).orElseThrow(()
                -> new NotFoundException(ErrorCode.NOT_FOUND_MENU));
    }
}
