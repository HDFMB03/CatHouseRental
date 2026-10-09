package com.Cathouse.project.ValueObject;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentChildVO {

    private Integer id;
    private Integer parentId;
    private Integer userId;
    private String username;
    private String avatar;
    private Integer replierId;
    private String replierName;
    private String replierAvatar;
    private String content;
    private Boolean replyInputStatus;
    private Boolean upvoteFlag;
    private Integer upvoteCount;
    private String contentType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
