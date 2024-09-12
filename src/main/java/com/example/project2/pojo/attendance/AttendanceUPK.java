package com.example.project2.pojo.attendance;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceUPK implements Serializable {

    private Long accountId;
    // private String date;

    private Date date;
}
