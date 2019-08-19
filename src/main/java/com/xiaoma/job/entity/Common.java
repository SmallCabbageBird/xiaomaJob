package com.xiaoma.job.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Common implements Serializable {

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone="GMT+8")
    private Date createTime;

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone="GMT+8")
    private Date updateTime;



}
