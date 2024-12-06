package com.tenten.outsourcing.bucket.controller;

import com.tenten.outsourcing.bucket.dto.BucketRequestDto;
import com.tenten.outsourcing.bucket.dto.BucketResponseDto;
import com.tenten.outsourcing.bucket.service.BucketService;
import com.tenten.outsourcing.common.LoginStatus;
import com.tenten.outsourcing.user.dto.SessionDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bucket")
public class BucketController {

    private final BucketService bucketService;

    /**
     * 장바구니 생성
     *
     * @param response     쿠키 생성을 위한 response
     * @param dto          메뉴 id, 가게 id
     * @param loginSession 현재 로그인한 유저 세션
     * @param cookie       장바구니 쿠키가 있는 경우 받아옴
     */
    @PostMapping
    public ResponseEntity<String> createBucket(
            HttpServletResponse response,
            @RequestBody @Valid BucketRequestDto dto,
            @SessionAttribute(name = LoginStatus.LOGIN_USER) SessionDto loginSession,
            @CookieValue(name = "bucket", required = false) Cookie cookie
    ) {

        String jsonString = (cookie != null)
                ? bucketService.createBucket(URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8), dto.getStoreId(), dto.getMenuId(), loginSession.getId())
                : bucketService.createBucket("", dto.getStoreId(), dto.getMenuId(), loginSession.getId());

        log.info(jsonString);

        Cookie newCookie = new Cookie("bucket", URLEncoder.encode(jsonString, StandardCharsets.UTF_8));
        newCookie.setMaxAge(60 * 60 * 24); // 하루
        response.addCookie(newCookie);

        return new ResponseEntity<>("장바구니에 메뉴가 추가되었습니다.", HttpStatus.CREATED);
    }

    /**
     * 자신의 장바구니 조회
     *
     * @param loginSession 현재 로그인한 유저 세션
     * @param cookie       장바구니 쿠키가 있으면 받아옴
     */
    @GetMapping
    public ResponseEntity<List<BucketResponseDto>> findBuckets(
            @SessionAttribute(name = LoginStatus.LOGIN_USER) SessionDto loginSession,
            @CookieValue(name = "bucket", required = false) Cookie cookie
    ) {

        List<BucketResponseDto> bucketsDto = (cookie != null)
                ? bucketService.findBuckets(URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8), loginSession.getId())
                : bucketService.findBuckets("", loginSession.getId());

        return ResponseEntity.ok().body(bucketsDto);
    }

    /**
     * 장바구니 메뉴 삭제. (개수 변경 X)
     *
     * @param loginSession 현재 로그인한 유저 세션
     * @param cookie       쿠키 장바구니 목록
     * @param dto          메뉴 Id, 가게  Id
     */
    @DeleteMapping
    public ResponseEntity<String> deleteBucket(
            HttpServletResponse response,
            @SessionAttribute(name = LoginStatus.LOGIN_USER) SessionDto loginSession,
            @CookieValue(name = "bucket") Cookie cookie,
            @RequestBody @Valid BucketRequestDto dto
    ) {

        String jsonString = bucketService.deleteBucket(loginSession.getId(), URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8), dto.getMenuId());

        Cookie newCookie = new Cookie("bucket", URLEncoder.encode(jsonString, StandardCharsets.UTF_8));
        newCookie.setMaxAge(60 * 60 * 24); // 하루
        response.addCookie(newCookie);

        return ResponseEntity.ok().body("장바구니에서 메뉴가 삭제되었습니다.");
    }
}
