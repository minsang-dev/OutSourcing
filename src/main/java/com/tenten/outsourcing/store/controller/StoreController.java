package com.tenten.outsourcing.store.controller;

import com.tenten.outsourcing.store.dto.StoreRequestDto;
import com.tenten.outsourcing.store.dto.StoreResponseDto;
import com.tenten.outsourcing.store.dto.StoreUpdateRequestDto;
import com.tenten.outsourcing.store.dto.StoreUpdateResponseDto;
import com.tenten.outsourcing.store.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/stores")
    public ResponseEntity<StoreResponseDto> create(@SessionAttribute(name = "LOGIN_USER", required = false) Long userId, @Valid @RequestBody StoreRequestDto requestDto) {

        return new ResponseEntity<>(storeService.create(userId, requestDto), HttpStatus.CREATED);
    }

    @GetMapping("/stores")
    public ResponseEntity<List<StoreResponseDto>> findByName(@RequestParam String name, @PageableDefault(page = 1) Pageable pageable) {
        return new ResponseEntity<>(storeService.findByName(name, pageable), HttpStatus.OK);
    }

    @PatchMapping("/stores/{storeId}")
    public ResponseEntity<StoreUpdateResponseDto> updateById(@SessionAttribute(name = "LOGIN_USER", required = false) Long userId, @PathVariable Long storeId, @RequestBody StoreUpdateRequestDto requestDto) {
        return new ResponseEntity<>(storeService.updateById(userId, storeId, requestDto), HttpStatus.OK);
    }

}
