package com.example.project2.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "clock")
public class ClockDo {

    public ClockDo() {
        id = 0L;
        username = null;
    }

    @Id
    private Long id;

    @NotBlank
    @Column(name = "username")
    private String username;

    @Column(name = "clock_in")
    @CreationTimestamp
    private Timestamp clockIn;

    @Column(name = "clock_out")
    @UpdateTimestamp
    private Timestamp clockOut;
}
