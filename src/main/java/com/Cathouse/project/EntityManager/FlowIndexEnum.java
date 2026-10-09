package com.Cathouse.project.EntityManager;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FlowIndexEnum {

    VIEW(1, "浏览"),
    COLLECTION(2, "收藏"),
    REMAIN(3, "停留"),
    SHOW(4, "展现"),
    EVALUATIONS(5, "评论");

    private final Integer type;
    private final String detail;

}
