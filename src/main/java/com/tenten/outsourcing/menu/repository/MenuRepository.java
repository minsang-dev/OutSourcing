package com.tenten.outsourcing.menu.repository;

import com.tenten.outsourcing.menu.entity.Menu;
import com.tenten.outsourcing.order.entity.BucketMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("SELECT m FROM Menu m WHERE m.store.id = :storeId AND m.deletedAt IS NULL")
    List<Menu> findAllMenuByStoreId(@Param("storeId") Long storeId);

    @Query("SELECT m FROM Menu m WHERE m.id IN :menuIds AND m.deletedAt IS NULL")
    List<Menu> findMenusInBucket(@Param("menuIds") List<Long> menuIds);

}