package com.tenten.outsourcing.menu.controller;

import com.tenten.outsourcing.menu.dto.MenuRequestDto;
import com.tenten.outsourcing.menu.dto.MenuResponseDto;
import com.tenten.outsourcing.menu.dto.MenuUpdateRequestDto;
import com.tenten.outsourcing.menu.dto.MenuUpdateResponseDto;
import com.tenten.outsourcing.menu.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores/{storeId}/menus")
public class MenuController {

    private final MenuService menuService;

    // 메뉴 생성
    @PostMapping
    public ResponseEntity<MenuResponseDto> createMenu(
            HttpServletRequest request,
            @PathVariable Long storeId,
            @RequestBody MenuRequestDto requestDto

    ) {

        MenuResponseDto responseDto = menuService.createMenu(
                request,
                storeId,
                requestDto.getMenuName(),
                requestDto.getMenuPictureUrl(),
                requestDto.getPrice()
        );

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);

    }

    // 메뉴 수정
    @PutMapping("/menuId")
    public ResponseEntity<MenuUpdateResponseDto> updateMenu(
            HttpServletRequest request,
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @RequestBody MenuUpdateRequestDto requestDto
    ) {
        MenuUpdateResponseDto responseDto = menuService.updateMenu(
                request,
                storeId,
                menuId,
                requestDto.getMenuName(),
                requestDto.getMenuPictureUrl(),
                requestDto.getPrice(),
                requestDto.getPassword()
        );

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
