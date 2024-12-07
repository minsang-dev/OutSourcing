package com.tenten.outsourcing.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tenten.outsourcing.common.Mapper;
import com.tenten.outsourcing.menu.entity.Menu;
import com.tenten.outsourcing.menu.service.MenuService;
import com.tenten.outsourcing.order.dto.BucketMenuResponseDto;
import com.tenten.outsourcing.order.entity.Bucket;
import com.tenten.outsourcing.order.entity.BucketMenu;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BucketService {

    private final MenuService menuService;

    /**
     * 장바구니 생성 또는 추가
     * 가게에 없는 메뉴일 경우 예외
     *
     * @param cookieValue 장바구니 쿠키 값
     * @param storeId     주문할 가게 식별자
     * @param menuId      장바구니에 담을 메뉴 식별자
     * @return Bucket -> Json -> String 파싱한 String
     */
    public String createBucket(String cookieValue, Long storeId, Long menuId) {

        Menu findMenu = menuService.findByIdOrElseThrow(menuId);
        if (!findMenu.getStore().getId().equals(storeId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        try {
            Bucket bucket = new Bucket(findMenu.getStore().getId());
            boolean isDuplicated = false;
            if (!cookieValue.isEmpty()) {
                bucket = Mapper.jsonStringToBucket(cookieValue);

                // 장바구니에 추가하려는 메뉴가 같은 가게의 메뉴가 맞을 경우
                if (bucket.getStoreId().equals(findMenu.getStore().getId())) {
                    // 장바구니에 같은 메뉴가 있을 경우 count update
                    for (BucketMenu bm : bucket.getBucketMenus()) {
                        if (bm.getMenuId().equals(findMenu.getId())) {
                            bm.addCount(1);
                            isDuplicated = true;
                            break;
                        }
                    }
                }
            }
            if (!isDuplicated) {
                bucket.getBucketMenus().add(new BucketMenu(findMenu.getId(), 1));
            }
            return Mapper.BucketToJsonString(bucket);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 로그인한 유저의 장바구니 조회
     *
     * @param cookieValue 장바구니 쿠키
     */
    public List<BucketMenuResponseDto> findBuckets(String cookieValue) {

        try {
            Bucket bucket = Mapper.jsonStringToBucket(cookieValue);

            return bucket.getBucketMenus().stream().map(m -> new BucketMenuResponseDto(m.getMenuId(), m.getCount())).toList();
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 장바구니 삭제. Count 무관하게 메뉴 통으로 삭제
     *
     * @param cookieValue 쿠키 값
     * @param menuId      삭제할 메뉴 식별자
     */
    public String deleteBucket(String cookieValue, Long menuId) {
        try {
            Bucket bucket = Mapper.jsonStringToBucket(cookieValue);
            Menu findMenu = menuService.findByIdOrElseThrow(menuId);
            bucket.getBucketMenus().removeIf(m -> m.getMenuId().equals(findMenu.getId()));

            return Mapper.BucketToJsonString(bucket);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
