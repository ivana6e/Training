package com.example.project2.dao;

import com.example.project2.pojo.user.UserDo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<UserDo, Long> {

    UserDo findByAccount(String account);
}
