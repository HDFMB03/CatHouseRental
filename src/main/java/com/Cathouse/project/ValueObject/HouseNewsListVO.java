package com.Cathouse.project.ValueObject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HouseNewsListVO {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private String cover;
    private String summary;
    private Integer viewNumber;
    private Integer saveNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
