package com.example.project2.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@Table(name = "clock")
public class ClockDo {

    @Id
    private Long id;

    @NotBlank
    @Column(name = "username")
    private String username;

    @Column(name = "clock_in")
    private Timestamp clockIn;

    @Column(name = "clock_out")
    private Timestamp clockOut;

    @Column(name = "message")
    private String message;
}
