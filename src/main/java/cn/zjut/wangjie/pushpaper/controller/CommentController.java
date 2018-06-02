package cn.zjut.wangjie.pushpaper.controller;

import cn.zjut.wangjie.pushpaper.pojo.Comment;
import cn.zjut.wangjie.pushpaper.pojo.PageDTO;
import cn.zjut.wangjie.pushpaper.pojo.User;
import cn.zjut.wangjie.pushpaper.service.CommentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    @Autowired
    private HttpServletRequest request;
    @Value("${page.pageSize}")
    private int pageSize;
    @PostMapping("/add")
    public String addComment(Comment comment){
        comment.setAddAt(System.currentTimeMillis());
        commentService.addComment(comment);
        return  "redirect:/paperController/showPaperInfo?id="+comment.getPaperId();
    }

    @GetMapping("/list-comment")
    public String getCommentList(@RequestParam(value="currentPage") Integer currentPage ){
        PageDTO commentPageDTO = (PageDTO)request.getSession().getAttribute("commentPage");
        if(commentPageDTO == null) {
            commentPageDTO = new PageDTO();
            commentPageDTO.setPageSize(pageSize);
        }
        if (currentPage == null){
            currentPage = 1;
        }
        commentPageDTO.setCurrentPage(currentPage);
        User user = (User)request.getSession().getAttribute("user");
        Page page = PageHelper.startPage(commentPageDTO.getCurrentPage(),commentPageDTO.getPageSize());
        List<Comment> commentList = commentService.listCommentByUserId(user.getUid());
        commentPageDTO.setTotalPage(page.getPages());
        commentPageDTO.setContentList(commentList);
        request.getSession().setAttribute("commentPage", commentPageDTO);
        return "commentList";
    }

}
