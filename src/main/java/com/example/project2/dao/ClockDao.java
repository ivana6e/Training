package com.example.project2.dao;

import com.example.project2.pojo.ClockDo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClockDao extends JpaRepository<ClockDo, Long> {

    ClockDo findByUsername(String username);
}
