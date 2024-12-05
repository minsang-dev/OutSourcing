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

}
