package com.example.project2.dao;

import com.example.project2.pojo.UserPojo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<UserPojo, Long> {

    UserPojo findByUsername(String username);
}
