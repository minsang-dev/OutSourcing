package com.tenten.outsourcing.order.entity;

import com.tenten.outsourcing.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BucketMenu {

    private Long menuId;

    private int count;

    private Menu menu = new Menu();

    public BucketMenu(Long menuId, int count) {
        this.menuId = menuId;
        this.count = count;
    }

    public void placeMenu(Menu menu) {
        this.menu = menu;
    }

    public void addCount(int change) {
        this.count += change;
    }
}
