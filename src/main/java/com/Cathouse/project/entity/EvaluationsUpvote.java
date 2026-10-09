package com.Cathouse.project.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EvaluationsUpvote {
    private Integer id;
    private Integer userId;
    private Integer evaluationsId;
}
