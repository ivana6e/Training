package com.example.project2.pojo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClockUPK implements Serializable {

    private Long id;
    private String date;
}
