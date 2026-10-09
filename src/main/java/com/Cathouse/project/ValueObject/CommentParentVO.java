package com.Cathouse.project.ValueObject;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentParentVO {

    private Integer id;
    private Integer userId;
    private String username;
    private String avatar;
    private String content;
    private Boolean showReplyInput;
    private Integer childTotal;
    private Boolean upvoteFlag;
    private Integer upvoteCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    private List<CommentChildVO> commentChildVOS;

}
