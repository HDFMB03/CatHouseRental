package com.Cathouse.project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class Evaluations {
    private Integer id;
    private Integer parentId;
    private Integer commenterId;
    private Integer replierId;
    private String contentType;
    private String content;
    private Integer contentId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
