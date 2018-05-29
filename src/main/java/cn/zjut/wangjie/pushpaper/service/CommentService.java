package cn.zjut.wangjie.pushpaper.service;


import cn.zjut.wangjie.pushpaper.pojo.Comment;

import java.util.List;

public interface CommentService {
    int addComment(Comment comment);

    List<Comment> listCommentByPaperId(Integer paperId);
}
