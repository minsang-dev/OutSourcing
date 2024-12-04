package com.tenten.outsourcing.service;

import com.tenten.outsourcing.dto.MenuResponseDto;
import com.tenten.outsourcing.entity.Menu;
import com.tenten.outsourcing.entity.User;
import com.tenten.outsourcing.repository.MenuRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRespository;

    @Transactional
    public MenuResponseDto createMenu(
            HttpServletRequest request, String menuName, String menuPictureUrl, Integer price) {

        //requestServlet을 통해 로그인한 사용자
        HttpSession session = request.getSession(false);

        Long ownerId = (Long) session.getAttribute("ownerId");

        User findUser = userRespository.findByIdOrElseThrow(ownerId);
        Menu savedMenu = menuRepository.save(new Menu(menuName,menuPictureUrl, price, findUser));

        return new MenuResponseDto(
                savedMenu.getId(),
                findUser.getId(),
                savedMenu.getMenuName(),
                savedMenu.getMenuPictureUrl(),
                savedMenu.getPrice(),
                savedMenu.getCreatedAt()
        );
    }
}
