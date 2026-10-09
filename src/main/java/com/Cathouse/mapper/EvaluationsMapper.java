package com.Cathouse.mapper;

import com.Cathouse.project.DataTransferObject.EvaluationsQueryDto;
import com.Cathouse.project.entity.Evaluations;
import com.Cathouse.project.ValueObject.CommentChildVO;
import com.Cathouse.project.ValueObject.CommentParentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EvaluationsMapper {

    List<CommentParentVO> getParentComments(@Param(value = "contentId") Integer contentId,
                                            @Param(value = "userId") Integer userId,
                                            @Param(value = "contentType") String contentType);
    List<CommentChildVO> query(EvaluationsQueryDto evaluationsQueryDto);
    Integer queryCount(EvaluationsQueryDto evaluationsQueryDto);
    List<Integer> selectChildComments(@Param(value = "ids") List<Integer> ids);

    void batchDelete(@Param(value = "ids") List<Integer> ids);
    Integer totalCount(Integer contentId, String contentType);
    void save(Evaluations evaluations);
    void update(Evaluations evaluations);

}
