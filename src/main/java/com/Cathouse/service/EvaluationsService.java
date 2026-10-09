package com.Cathouse.service;

import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.EvaluationsQueryDto;
import com.Cathouse.project.entity.Evaluations;

import java.util.List;


public interface EvaluationsService {

    Result<Object> insert(Evaluations evaluations);

    Result<Object> list(Integer contentId, String contentType);

    Result<Object> query(EvaluationsQueryDto evaluationsQueryDto);

    Result<String> deleteById(Integer id);

    Result<Void> update(Evaluations evaluations);

    Result<Object> upvoteOperation(Evaluations evaluations);

    Result<Object> batchDelete(List<Integer> ids);
}
