package com.example.project2.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "clock_diff")
public class ClockDiffDo {

    @Id
    private Long id;

    @NotBlank
    @Column(name = "username")
    private String username;

    @Column(name = "clock_diff")
    private Timestamp clockDiff;
}
