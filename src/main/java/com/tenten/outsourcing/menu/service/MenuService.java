package com.tenten.outsourcing.menu.service;

import com.tenten.outsourcing.exception.ErrorCode;
import com.tenten.outsourcing.exception.NotFoundException;
import com.tenten.outsourcing.menu.dto.MenuResponseDto;
import com.tenten.outsourcing.menu.dto.MenuUpdateResponseDto;
import com.tenten.outsourcing.menu.entity.Menu;
import com.tenten.outsourcing.menu.repository.MenuRepository;
import com.tenten.outsourcing.order.entity.BucketMenu;
import com.tenten.outsourcing.store.entity.Store;
import com.tenten.outsourcing.store.repository.StoreRepository;
import com.tenten.outsourcing.user.entity.User;
import com.tenten.outsourcing.user.service.UserService;
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
    private final UserService userService;

    /**
     * 메뉴 생성
     * 로그인한 유저가 가게의 점주가 맞는지 비교
     */
    @Transactional
    public MenuResponseDto createMenu(Long userId, Long storeId, String menuName, String menuPictureUrl, Integer price) {
  
        Store findStore = checkOwner(userId, storeId);

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

    /**
     * 메뉴 다건 조회
     * 가게 조회 시 함께 조회
     */
    @Transactional
    public List<Menu> getMenusByStoreId(Long storeId) {
        return menuRepository.findAllMenuByStoreId(storeId);
    }

    /**
     * 메뉴 단일 조회
     */
    public Menu findByIdOrElseThrow(Long menuId) {
        return menuRepository.findById(menuId).orElseThrow(()
                -> new NotFoundException(ErrorCode.NOT_FOUND_MENU));
    }

    /**
     * 메뉴 업데이트
     * 가게 점주인지 확인 후 메뉴 업데이트 가능
     */
    @Transactional
    public MenuUpdateResponseDto updateMenu(Long userId, Long storeId, Long menuId, String menuName, String menuPictureUrl, Integer price) {

       checkOwner(userId, storeId);


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

    /**
     * 메뉴 삭제
     * 로그인한 유저 확인 후 비밀번호 일치 여부 확인
     */
    public void deleteMenu(Long userId, Long storeId, Long menuId, String password) {

        checkOwner(userId, storeId);

        User findUser = userService.findByIdOrElseThrow(userId);

        userService.checkPasswordMatch(findUser.getPassword(), password);

        Menu findMenu = menuRepository.
                findById(menuId).
                orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_MENU));

        findMenu.deleteMenu();
        menuRepository.save(findMenu);
    }

    /**
     * 로그인한 유저가 가게 사장인지 체크
     */
    private Store checkOwner(Long userId, Long storeId) {

        User findUser = userService.findByIdOrElseThrow(userId);
        Store findStore = storeRepository.findById(storeId).orElseThrow(()
                -> new NotFoundException(ErrorCode.NOT_FOUND_STORE));

        Long findUserId = findStore.getUser().getId();
        if (!findUserId.equals(findUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return findStore;
    }

    /**
     * 장바구니에 들어있는 메뉴 중 삭제되지 않은 메뉴 목록 반환
     *
     * @param bucketMenus 장바구니 메뉴 목록
     * @return 삭제되지 않은 장바구니 내 Menu 목록
     */
    public List<Menu> findOrderedMenu(List<BucketMenu> bucketMenus) {
        List<Long> bucketMenuIds = bucketMenus.stream().map(BucketMenu::getMenuId).toList();
        return menuRepository.findMenusInBucket(bucketMenuIds);
    }
}
