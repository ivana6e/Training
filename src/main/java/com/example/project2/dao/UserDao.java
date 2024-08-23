package com.example.project2.dao;

import com.example.project2.pojo.UserDo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<UserDo, Long> {

    UserDo findByUsername(String username);
}
