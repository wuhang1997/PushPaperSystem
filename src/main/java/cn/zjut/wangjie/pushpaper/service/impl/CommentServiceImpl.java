package cn.zjut.wangjie.pushpaper.service.impl;

import cn.zjut.wangjie.pushpaper.mapper.CommentDao;
import cn.zjut.wangjie.pushpaper.pojo.Comment;
import cn.zjut.wangjie.pushpaper.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: pushpaper
 * @description:
 * @author: WangJie
 * @create: 2018-05-29 18:10
 **/
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public int addComment(Comment comment) {
        return commentDao.addComment(comment);
    }

    @Override
    public List<Comment> listCommentByPaperId(Integer paperId) {
        return commentDao.listCommentByPaperId(paperId);
    }
}
