package com.example.project2.pojo.attendance;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@IdClass(AttendanceUPK.class)
// @Table(name = "attendance")
@Table(name = "attendance_time")
public class AttendanceDo {

    // @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    // @Column(name = "id")
    // private Long id;

    @Id
    @Column(name = "account_id")
    private Long accountId;

    // @Id
    // @Column(name = "date", length = 10)
    // private String date;

    @Id
    @Column(name = "date")
    private Date date;

    @Column(name = "clock_in")
    private Timestamp clockIn;

    @Column(name = "clock_out")
    private Timestamp clockOut;

    // @Column(name = "working_hours", length = 10)
    // private String workingHours;

    @Column(name = "working_hours")
    private Time workingHours;

    @Column(name = "state", length = 50)
    private String state; // clock in/out success/fail, forget
}
