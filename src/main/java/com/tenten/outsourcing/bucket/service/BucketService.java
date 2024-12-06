package com.tenten.outsourcing.bucket.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tenten.outsourcing.bucket.dto.BucketResponseDto;
import com.tenten.outsourcing.bucket.entity.Bucket;
import com.tenten.outsourcing.menu.entity.Menu;
import com.tenten.outsourcing.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
     * @param userId      로그인한 유저 식별자
     * @return Bucket -> Json -> String 파싱한 String
     */
    public String createBucket(String cookieValue, Long storeId, Long menuId, Long userId) {

        Menu findMenu = menuService.findByIdOrElseThrow(menuId);
        if (!findMenu.getStore().getId().equals(storeId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        try {
            List<Bucket> bucketList = new ArrayList<>();
            boolean isDuplicated = false;
            if (!cookieValue.isEmpty()) {
                bucketList = filterBucketsByUser(Bucket.jsonStringToBuckets(cookieValue), userId);

                // 장바구니에 추가하려는 메뉴가 같은 가게의 메뉴가 맞을 경우
                if (bucketList.get(0).getStoreId().equals(findMenu.getStore().getId())) {
                    // 장바구니에 같은 메뉴가 있을 경우 count update
                    for (Bucket b : bucketList) {
                        if (b.getMenuId().equals(findMenu.getId())) {
                            b.updateCount(1);
                            isDuplicated = true;
                            break;
                        }
                    }
                }
            }
            if (!isDuplicated) {
                bucketList.add(new Bucket(userId, storeId, findMenu.getId(), 1));
            }
            return Bucket.BucketToJsonString(bucketList);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* * 로그인한 유저의 장바구니 목록을 조회
     *
     * @param cookieValue 장바구니 쿠키 값
     * @param userId      로그인한 유저 식별자
     */
    public List<BucketResponseDto> findBuckets(String cookieValue, Long userId) {
        if (cookieValue.isEmpty()) {
            return new ArrayList<>();
        }

        try {
            List<Bucket> usersList = filterBucketsByUser(Bucket.jsonStringToBuckets(cookieValue), userId);

            return usersList.stream().map(BucketResponseDto::new).toList();
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 장바구니 삭제. Count 무관하게 메뉴 통으로 삭제
     *
     * @param userId      유저 식별자
     * @param cookieValue 쿠키 값
     * @param menuId      삭제할 메뉴 식별자
     */
    public String deleteBucket(Long userId, String cookieValue, Long menuId) {
        try {
            List<Bucket> usersList = new ArrayList<>(filterBucketsByUser(Bucket.jsonStringToBuckets(cookieValue), userId));

            boolean isExist = false;
            for (int i = 0; i < usersList.size(); i++) {
                if (usersList.get(i).getMenuId().equals(menuId)) {
                    usersList.remove(usersList.get(i));
                    isExist = true;
                    break;
                }
            }

            if (!isExist) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            return Bucket.BucketToJsonString(usersList);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 특정 유저의 장바구니만 필터링
     *
     * @param list   쿠키에 있던 장바구니들
     * @param userId 유저 식별자
     * @return {@code userId}에 해당하는 장바구니 목록
     */
    public List<Bucket> filterBucketsByUser(List<Bucket> list, Long userId) {
        return list.stream().filter(b -> b.getUserId().equals(userId)).collect(Collectors.toList());
    }
}
