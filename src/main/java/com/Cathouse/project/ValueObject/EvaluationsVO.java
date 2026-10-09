package com.Cathouse.project.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationsVO {

    private Integer count;
    private List<CommentParentVO> data;
}
