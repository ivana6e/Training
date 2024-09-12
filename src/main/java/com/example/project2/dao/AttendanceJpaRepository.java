package com.example.project2.dao;

import com.example.project2.pojo.attendance.AttendanceDo;
import com.example.project2.pojo.attendance.AttendanceUPK;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceJpaRepository extends JpaRepository<AttendanceDo, AttendanceUPK> {
}
