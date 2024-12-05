package com.tenten.outsourcing.menu.controller;

import com.tenten.outsourcing.common.LoginStatus;
import com.tenten.outsourcing.menu.dto.*;
import com.tenten.outsourcing.menu.entity.Menu;
import com.tenten.outsourcing.menu.service.MenuService;
import com.tenten.outsourcing.user.dto.SessionDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores/{storeId}/menus")
public class MenuController {

    private final MenuService menuService;

    // 메뉴 생성
    @PostMapping
    public ResponseEntity<MenuResponseDto> createMenu(
            @PathVariable Long storeId,
            @RequestBody MenuRequestDto requestDto,
            @SessionAttribute(name = LoginStatus.LOGIN_USER) SessionDto session
    ) {

        MenuResponseDto responseDto = menuService.createMenu(
                session.getId(),
                storeId,
                requestDto.getMenuName(),
                requestDto.getMenuPictureUrl(),
                requestDto.getPrice()
        );

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);

    }

    // 메뉴 다건 조회
    @GetMapping
    public ResponseEntity<List<Menu>> getMenuByStoreId(@PathVariable Long storeId) {
        List<Menu> menus = menuService.getMenusByStoreId(storeId);

        return new ResponseEntity<>(menus, HttpStatus.OK);

    }

    // 메뉴 수정
    @PutMapping("/{menuId}")
    public ResponseEntity<MenuUpdateResponseDto> updateMenu(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @RequestBody MenuUpdateRequestDto requestDto,
            @SessionAttribute(name = LoginStatus.LOGIN_USER) SessionDto session
    ) {
        MenuUpdateResponseDto responseDto = menuService.updateMenu(
                session.getId(),
                storeId,
                menuId,
                requestDto.getMenuName(),
                requestDto.getMenuPictureUrl(),
                requestDto.getPrice()
        );

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 메뉴 삭제
    @DeleteMapping("/{menuId}")
    public ResponseEntity<MenuResponseDto> deleteMenu(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @RequestBody MenuDeleteRequestDto requestDto,
            @SessionAttribute(name = LoginStatus.LOGIN_USER) SessionDto session
    ) {
        menuService.deleteMenu(session.getId(), storeId, menuId, requestDto.getPassword());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
