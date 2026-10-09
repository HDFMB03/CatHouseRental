package com.Cathouse.project.ValueObject;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseFlowIndexVO {

    private Integer id;
    private String houseName;
    private String houseCover;
    private Integer showNumber;
    private Integer viewNumber;
    private Double clickRate;
    private Integer saveNumber;
    private Integer evaluationsNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
