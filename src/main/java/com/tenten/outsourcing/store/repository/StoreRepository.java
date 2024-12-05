package com.tenten.outsourcing.store.repository;

import com.tenten.outsourcing.store.entity.Store;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query(value = "SELECT count(s.user.id) "
            + "FROM Store s "
            + "WHERE s.user.id = :id "
            + "AND s.deletedAt IS NULL")
    Long findRegisteredStore(@Param("id") Long id);


    @Query(value = "SELECT s FROM Store s WHERE s.name like %:name% AND s.deletedAt IS NULL")
    List<Store> findByNameContaining(@Param("name") String name, Pageable pageable);
}
