package com.tenten.outsourcing.controller;

import com.tenten.outsourcing.dto.StoreRequestDto;
import com.tenten.outsourcing.dto.StoreResponseDto;
import com.tenten.outsourcing.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/stores")
    public ResponseEntity<StoreResponseDto> create(@SessionAttribute(name = "USER_ID", required = false) Long userId, @Valid @RequestBody StoreRequestDto requestDto) {

        return new ResponseEntity<>(storeService.create(userId, requestDto),HttpStatus.CREATED);
    }
}
