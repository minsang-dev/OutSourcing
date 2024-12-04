package com.tenten.outsourcing.repository;

import com.tenten.outsourcing.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MenuRepository extends JpaRepository<Menu, Integer> {

}
