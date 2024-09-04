package com.example.project2.pojo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@IdClass(ClockUPK.class)
@Table(name = "clock")
public class ClockDo {

    @Id
    @Column(name = "id")
    private Long id;

    @Id
    @Column(name = "date")
    private String date;

    @Column(name = "clock_in")
    private Timestamp clockIn;

    @Column(name = "clock_out")
    private Timestamp clockOut;

    @Column(name = "message")
    private String message;
}
