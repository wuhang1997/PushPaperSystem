package cn.zjut.wangjie.pushpaper.controller;

import cn.zjut.wangjie.pushpaper.pojo.Comment;
import cn.zjut.wangjie.pushpaper.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: pushpaper
 * @description:
 * @author: WangJie
 * @create: 2018-05-29 18:08
 **/
@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @PostMapping("/add")
    public String addComment(Comment comment){
        comment.setAddAt(System.currentTimeMillis());
        commentService.addComment(comment);
        return  null;
    }
}
