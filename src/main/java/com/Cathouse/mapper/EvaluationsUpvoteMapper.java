package com.Cathouse.mapper;

import com.Cathouse.project.entity.EvaluationsUpvote;

public interface EvaluationsUpvoteMapper {

    void save(EvaluationsUpvote evaluationsUpvote);

    void delete(EvaluationsUpvote evaluationsUpvote);

    int queryCount(EvaluationsUpvote evaluationsUpvote);

}
