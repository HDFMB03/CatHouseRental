package com.Cathouse.service.impl;

import com.Cathouse.context.LocalThreadHolder;
import com.Cathouse.mapper.EvaluationsMapper;
import com.Cathouse.mapper.EvaluationsUpvoteMapper;
import com.Cathouse.mapper.UserMapper;
import com.Cathouse.project.api.ApiResult;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.EvaluationsQueryDto;
import com.Cathouse.project.entity.Evaluations;
import com.Cathouse.project.entity.EvaluationsUpvote;
import com.Cathouse.project.entity.User;
import com.Cathouse.project.ValueObject.CommentChildVO;
import com.Cathouse.project.ValueObject.CommentParentVO;
import com.Cathouse.project.ValueObject.EvaluationsVO;
import com.Cathouse.service.EvaluationsService;
import com.Cathouse.utils.AhoCorasickFilter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EvaluationsServiceImpl implements EvaluationsService {

    @Resource
    private EvaluationsMapper evaluationsMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private EvaluationsUpvoteMapper evaluationsUpvoteMapper;
    @Override
    public Result<Object> insert(Evaluations evaluations) {
        String content = evaluations.getContent();
        AhoCorasickFilter ahoCorasickFilter = new AhoCorasickFilter();
        for (String sensitiveWord : AhoCorasickFilter.sensitiveWords) {
            ahoCorasickFilter.addWord(sensitiveWord);
        }
        ahoCorasickFilter.buildFailurePointer();
        String filteredContent = ahoCorasickFilter.filter(content);
        evaluations.setContent(filteredContent);
        evaluations.setCommenterId(LocalThreadHolder.getUserId());
        User user = userMapper.getUserById(LocalThreadHolder.getUserId());
        evaluations.setCreateTime(LocalDateTime.now());
        evaluationsMapper.save(evaluations);
        return ApiResult.success("评论成功");
    }

    @Override
    public Result<Object> list(Integer contentId, String contentType) {
        List<CommentParentVO> parentComments = evaluationsMapper.getParentComments(
                contentId,
                LocalThreadHolder.getUserId(),
                contentType
        );
        Integer count = evaluationsMapper.totalCount(contentId, contentType);
        return ApiResult.success(new EvaluationsVO(count, parentComments));
    }

    @Override
    public Result<Object> query(EvaluationsQueryDto evaluationsQueryDto) {
        List<CommentChildVO> list = evaluationsMapper.query(evaluationsQueryDto);
        Integer totalPage = evaluationsMapper.queryCount(evaluationsQueryDto);
        return ApiResult.success(list, totalPage);
    }

    @Override
    public Result<String> deleteById(Integer id) {
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(id);
        evaluationsMapper.batchDelete(ids);
        return ApiResult.success();
    }

    @Override
    public Result<Void> update(Evaluations evaluations) {
        evaluationsMapper.update(evaluations);
        return ApiResult.success();
    }

    @Override
    public Result<Object> upvoteOperation(Evaluations evaluations) {
        Integer userId = LocalThreadHolder.getUserId();
        Integer evaluationsId = evaluations.getId();
        EvaluationsUpvote query = new EvaluationsUpvote();
        query.setUserId(userId);
        query.setEvaluationsId(evaluationsId);

        boolean hasUpvote = evaluationsUpvoteMapper.queryCount(query) > 0;

        if (hasUpvote) {
            EvaluationsUpvote deleteCondition = new EvaluationsUpvote();
            deleteCondition.setUserId(userId);
            deleteCondition.setEvaluationsId(evaluationsId);
            evaluationsUpvoteMapper.delete(deleteCondition);
        } else {
            EvaluationsUpvote newUpvote = new EvaluationsUpvote();
            newUpvote.setUserId(userId);
            newUpvote.setEvaluationsId(evaluationsId);
            evaluationsUpvoteMapper.save(newUpvote);
        }

        EvaluationsUpvote countQuery = new EvaluationsUpvote();
        countQuery.setEvaluationsId(evaluationsId);
        int total = evaluationsUpvoteMapper.queryCount(countQuery);
        Map<String, Object> rep = new HashMap<>();
        rep.put("count", total);
        rep.put("haveUpvote", !hasUpvote);

        return ApiResult.success(rep);
    }

    @Override
    public Result<Object> batchDelete(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return ApiResult.error("删除ID列表不能为空");
        }

        try {
            List<Integer> childIds = evaluationsMapper.selectChildComments(ids);
            if (childIds != null && !childIds.isEmpty()) {
                ids.addAll(childIds);
            }
            evaluationsMapper.batchDelete(ids);
            Map<String, Object> result = new HashMap<>();
            result.put("deletedCount", ids.size());
            result.put("message", "成功删除 " + ids.size() + " 条评论");
            return ApiResult.success(result);
        } catch (Exception e) {
            return ApiResult.fail("批量删除失败: " + e.getMessage());
        }
    }
}
