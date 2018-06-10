package cn.zjut.wangjie.pushpaper.controller;

import cn.zjut.wangjie.pushpaper.pojo.*;
import cn.zjut.wangjie.pushpaper.service.CollectionService;
import cn.zjut.wangjie.pushpaper.service.CommentService;
import cn.zjut.wangjie.pushpaper.service.NoteService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: pushpaper
 * @description:
 * @author: WangJie
 * @create: 2018-06-09 15:58
 **/
@Controller
@RequestMapping("/note")
public class NoteController {
    @Autowired
    private NoteService noteService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private CommentService commentService;
    @Value("${page.pageSize}")
    private int pageSize;
    @RequestMapping("/add")
    public String addNote(Note note){

        noteService.addNote(note);
        return "redirect:/paperController/showPaperInfo?id="+note.getPaperId();
    }

    @GetMapping("/{noteId}/show")
    public String showNote(@PathVariable("noteId") Integer noteId  ){
        Note note = noteService.getNoteById(noteId);
        request.setAttribute("noteShowing", note);
        Collection collection = new Collection();
        User user = (User) request.getSession().getAttribute("user");

        collection.setPaperId(note.getPaperId());
        collection.setUserId(user.getUid());
        boolean isCollected = collectionService.isCollection(collection);
        request.setAttribute("isCollected", isCollected);
        //获取评论
        List<Comment> comments = commentService.listCommentByPaperId(note.getPaperId());
        request.setAttribute("commentList", comments);
        //获取笔记
        List<Note> noteList = noteService.listNoteByPaperIdAndUserId(note.getPaperId(), user.getUid());
        request.setAttribute("noteList", noteList);

        return "noteShow";
    }

    @PostMapping("/update")
    public String updateNote(Note note){
        noteService.updateNote(note);
        return "redirect:/paperController/showPaperInfo?id="+note.getPaperId();
    }

    @PostMapping("/delete")
    @ResponseBody
    public String deleteNote( Integer noteId){
        int result = noteService.deleteNote(noteId);
        return result==1? "success": "fail";
    }

    @GetMapping("/listShow")
    public String listNotesShow( @RequestParam(value="currentPage") Integer currentPage ){
        PageDTO notePageDTO = (PageDTO)request.getSession().getAttribute("notePage");
        if(notePageDTO==null) {
            notePageDTO = new PageDTO();
            notePageDTO.setPageSize(pageSize);
        }
        if (currentPage == null){
            currentPage = 1;
        }
        notePageDTO.setCurrentPage(currentPage);
        User user = (User)request.getSession().getAttribute("user");
        Page page = PageHelper.startPage(currentPage,pageSize);
        List<Note> noteList = noteService.listNoteByUserId(user.getUid());
        notePageDTO.setPageSize(pageSize);
        notePageDTO.setTotalPage(page.getPages());
        notePageDTO.setContentList(noteList);
        request.getSession().setAttribute("notePage", notePageDTO);
        return "noteListShow";
    }
}


