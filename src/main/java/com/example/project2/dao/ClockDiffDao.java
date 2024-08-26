package com.example.project2.dao;

import com.example.project2.pojo.ClockDiffDo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClockDiffDao extends JpaRepository<ClockDiffDo, Long> {
}
