package com.tenten.outsourcing.menu.controller;

import com.tenten.outsourcing.menu.dto.MenuRequestDto;
import com.tenten.outsourcing.menu.dto.MenuResponseDto;
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

    @PostMapping
    public ResponseEntity<MenuResponseDto> createMenu(
            HttpServletRequest request,
            @RequestBody MenuRequestDto requestDto
    ) {

        MenuResponseDto responseDto = menuService.createMenu(
                request,
                requestDto.getMenuName(),
                requestDto.getMenuPictureUrl(),
                requestDto.getPrice()
        );

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);

    }
}