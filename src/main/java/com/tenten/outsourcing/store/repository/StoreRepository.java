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

    @Query(value = "Select count(s.user.id) "
            + "from Store s "
            + "where s.user.id = :id "
            + "and s.deletedAt is null")
    List<Store> findByUserId(@Param("id") Long id);

    List<Store> findByNameLike(String name, Pageable pageable);
}
