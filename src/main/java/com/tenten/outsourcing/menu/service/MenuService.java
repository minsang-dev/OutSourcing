package com.tenten.outsourcing.menu.service;

import com.tenten.outsourcing.config.PasswordEncoder;
import com.tenten.outsourcing.menu.dto.MenuResponseDto;
import com.tenten.outsourcing.menu.dto.MenuUpdateResponseDto;
import com.tenten.outsourcing.menu.entity.Menu;
import com.tenten.outsourcing.menu.repository.MenuRepository;
import com.tenten.outsourcing.store.entity.Store;
import com.tenten.outsourcing.store.repository.StoreRepository;
import com.tenten.outsourcing.user.entity.User;
import com.tenten.outsourcing.user.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final LoginService loginService ;
    private final PasswordEncoder passwordEncoder;

    // 메뉴 생성
    @Transactional
    public MenuResponseDto createMenu(
            HttpServletRequest request, Long storeId, String menuName, String menuPictureUrl, Integer price) {

        // requestServlet을 통해 로그인한 사용자 중 사업자 ID 가져오기
        HttpSession session = request.getSession(false);
        Long ownerId = (Long) session.getAttribute("ownerId");

        // store 안에 있는 userId와 로그인의 정보 비교
        User findUser = loginService.findByIdOrElseThrow(ownerId);
        Store findStore = storeRepository.findById(storeId).orElseThrow(()
                -> new IllegalArgumentException("가게를 찾을 수 없습니다."));

        Long findUserId = findStore.getUser().getId();
        if (!findUserId.equals(findUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

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

    // 메뉴 수정 로직
    @Transactional
    public MenuUpdateResponseDto updateMenu(
            HttpServletRequest request, Long storeId, Long menuId, String menuName, String menuPictureUrl, Integer price, String password) {

        // requestServlet을 통해 로그인한 사용자 중 사업자 ID 가져오기
        HttpSession session = request.getSession(false);
        Long ownerId = (Long) session.getAttribute("ownerId");

        // store 안에 있는 userId와 로그인의 정보 비교
        User findUser = loginService.findByIdOrElseThrow(ownerId);
        Store findStore = storeRepository.findById(storeId).orElseThrow(()
                -> new IllegalArgumentException("가게를 찾을 수 없습니다."));

        Long findUserId = findStore.getUser().getId();
        if (!findUserId.equals(findUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

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

    private Menu findByIdOrElseThrow(Long menuId) {
        return menuRepository.findById(menuId).orElseThrow(()
                -> new IllegalArgumentException("메뉴를 찾을 수 없습니다."));
    }
}
