package com.tenten.outsourcing.store.controller;

import com.tenten.outsourcing.common.LoginStatus;
import com.tenten.outsourcing.store.dto.*;
import com.tenten.outsourcing.store.service.StoreService;
import com.tenten.outsourcing.user.dto.SessionDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/stores")
    public ResponseEntity<StoreResponseDto> create(
            @SessionAttribute(name = LoginStatus.LOGIN_USER) SessionDto session,
            @Valid @RequestBody StoreRequestDto requestDto
    ) {
        return new ResponseEntity<>(storeService.create(session, requestDto), HttpStatus.CREATED);
    }

    @GetMapping("/stores")
    public ResponseEntity<List<StoreResponseDto>> findByName(
            @RequestParam String name,
            @PageableDefault(page = 1) Pageable pageable
    ) {
        return new ResponseEntity<>(storeService.findByName(name, pageable), HttpStatus.OK);
    }

    @GetMapping("/stores/{storeId}")
    public ResponseEntity<StoreDetailResponseDto> findById(@PathVariable Long storeId) {
        return new ResponseEntity<>(storeService.findById(storeId), HttpStatus.OK);
    }

    @PatchMapping("/stores/{storeId}")
    public ResponseEntity<StoreUpdateResponseDto> updateById(
            @SessionAttribute(name = LoginStatus.LOGIN_USER) SessionDto session,
            @PathVariable Long storeId, @RequestBody StoreUpdateRequestDto requestDto
    ) {
        return new ResponseEntity<>(storeService.updateById(session, storeId, requestDto), HttpStatus.OK);
    }

}
