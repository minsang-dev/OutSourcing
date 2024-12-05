package com.tenten.outsourcing.menu.repository;

import com.tenten.outsourcing.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("SELECT m FROM Menu m WHERE m.store.id = :storeId AND m.deletedAt IS NOT NULL")

    List<Menu> findAllMenuByStoreId(@Param("storeId") Long storeId);

}