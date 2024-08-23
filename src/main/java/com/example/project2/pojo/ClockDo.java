package com.example.project2.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "clock")
@EntityListeners(AuditingEntityListener.class)
public class ClockDo {

    @Id
    private Long id;

    @NotBlank
    @Column(name = "username", length = 20, nullable = false)
    private String username;

    @Column(name = "clock_in")
    @CreationTimestamp
    private Timestamp clockIn;

    @Column(name = "clock_out")
    @UpdateTimestamp
    private Timestamp clockOut;
}
