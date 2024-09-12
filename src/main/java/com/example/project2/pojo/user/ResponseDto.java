package com.example.project2.pojo.user;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class ResponseDto<Result> {

    private int status;
    private Result result;
    private String errorMessage;
}
