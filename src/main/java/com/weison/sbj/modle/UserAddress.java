package com.weison.sbj.modle;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.time.LocalDate;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAddress {

    private String name = null;

    private Integer age = null;

    private Sex sex = null;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate birthDay;

    private Long addressId;

    private String version;

    @ApiModel(description = "性别")
    public enum Sex {
        DEFAULT,
        MALE,
        FEMALE
    }
}
