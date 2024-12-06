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
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    /**
     * 입력한 가게 정보를 생성하고 결과를 반환합니다.
     * 생성 작업은 권한이 있는 사용자만 수행 할 수 있습니다.
     *
     * @param session 사용자 ID 및 권한
     * @param requestDto 가게 생성 요청 데이터
     * @return 등록된 가게 데이터
     */
    @PostMapping
    public ResponseEntity<StoreResponseDto> create(
            @SessionAttribute(name = LoginStatus.LOGIN_USER) SessionDto session,
            @Valid @RequestBody StoreRequestDto requestDto
    ) {
        return new ResponseEntity<>(storeService.create(session, requestDto), HttpStatus.CREATED);
    }

    /**
     * 입력받은 단어를 포함한 이름의 가게 목록을 반환합니다.
     *
     * @param name 가게 검색어
     * @param pageable 페이지 번호, 페이지당 표시할 개수
     * @return 가게 목록
     */
    @GetMapping
    public ResponseEntity<List<StoreResponseDto>> findByName(
            @RequestParam String name,
            @PageableDefault(page = 1) Pageable pageable
    ) {
        return new ResponseEntity<>(storeService.findByName(name, pageable), HttpStatus.OK);
    }

    /**
     * 선택한 가게의 정보와 메뉴를 반환합니다.
     *
     * @param storeId 가게 ID
     * @return 가게 정보 및 메뉴
     */
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreDetailResponseDto> findDetailById(@PathVariable Long storeId) {
        return new ResponseEntity<>(storeService.findDetailById(storeId), HttpStatus.OK);
    }

    /**
     * 선택한 가게에 수정 내용을 적용하고 결과를 반환합니다.
     * 수정 작업은 권한이 있는 사용자만 수행할 수 있습니다.
     *
     * @param session 사용자의 ID 및 권한
     * @param storeId 가게 ID
     * @param requestDto 수정할 내용
     * @return 변경된 내용
     */
    @PatchMapping("{storeId}")
    public ResponseEntity<StoreUpdateResponseDto> updateById(
            @SessionAttribute(name = LoginStatus.LOGIN_USER) SessionDto session,
            @PathVariable Long storeId,
            @Valid @RequestBody StoreUpdateRequestDto requestDto
    ) {
        return new ResponseEntity<>(storeService.updateById(session, storeId, requestDto), HttpStatus.OK);
    }

    /**
     * 선택한 가게를 삭제하고 그 결과를 반환합니다.
     * 삭제 작업은 권한이 있는 사용자만 수행할 수 있습니다.
     *
     * @param session 사용자의 ID 및 권한
     * @param storeId 가게 ID
     * @return 요청 결과
     */
    @DeleteMapping("{storeId}")
    public ResponseEntity<Void> deleteById(@SessionAttribute(name = LoginStatus.LOGIN_USER) SessionDto session, @PathVariable Long storeId) {
        storeService.deleteById(session, storeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
