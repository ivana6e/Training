package com.example.project2.pojo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@IdClass(ClockUPK.class)
@Table(name = "clock_diff")
public class ClockDiffDo {

    @Id
    @Column(name = "id")
    private Long id;

    @Id
    @Column(name = "date")
    private String date;

    @Column(name = "clock_diff")
    private String clockDiff;
}
