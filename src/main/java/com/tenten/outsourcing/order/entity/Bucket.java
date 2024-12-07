package com.tenten.outsourcing.order.entity;

import com.tenten.outsourcing.menu.entity.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@Slf4j
@NoArgsConstructor
public class Bucket {

    private Long storeId;

    private List<BucketMenu> bucketMenus = new ArrayList<>();

    public Bucket(Long storeId) {
        this.storeId = storeId;
    }

    /**
     * 삭제된 메뉴를 장바구니 목록에서 제거
     *
     * @param existMenuList 삭제되지 않은 메뉴 목록
     */
    public void removeDeletedMenus(List<Menu> existMenuList) {

        List<Long> existIds = existMenuList.stream().map(Menu::getId).toList();

        this.bucketMenus = this.bucketMenus.stream().filter(m ->
                existIds.contains(m.getMenuId())
        ).toList();
    }
}
