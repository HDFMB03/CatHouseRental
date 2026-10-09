package com.Cathouse.project.ValueObject;

import lombok.Data;

@Data
public class ScoreVO {

    private Integer userId;
    private Integer contentId;
    private Double score;
}
