package cn.zjut.wangjie.pushpaper.controller;

import cn.zjut.wangjie.pushpaper.pojo.PageDTO;
import cn.zjut.wangjie.pushpaper.pojo.PaperBrowseHistory;
import cn.zjut.wangjie.pushpaper.pojo.PaperInfo;
import cn.zjut.wangjie.pushpaper.pojo.User;
import cn.zjut.wangjie.pushpaper.service.PaperBrowseHistoryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: pushpaper
 * @description:
 * @author: WangJie
 * @create: 2018-05-24 17:02
 **/
@Controller
@RequestMapping("/paper-browse-history")
public class PaperBrowseHistoryController {
    @Autowired
    private PaperBrowseHistoryService paperBrowseHistoryService;
    @Autowired
    private HttpServletRequest request;
    @Value("${page.pageSize}")
    private int pageSize;
    @GetMapping("/show")
    public String collectionsShow( @RequestParam(value="currentPage") Integer currentPage ){
        PageDTO historyPageDTO = (PageDTO)request.getSession().getAttribute("historyPage");
        if(historyPageDTO==null) {
            historyPageDTO = new PageDTO();
            historyPageDTO.setPageSize(pageSize);
        }
        if (currentPage == null){
            currentPage = 1;
        }
        historyPageDTO.setCurrentPage(currentPage);
        User user = (User)request.getSession().getAttribute("user");
        Page page = PageHelper.startPage(currentPage,pageSize);
        List<PaperBrowseHistory> paperBrowseHistoryList = paperBrowseHistoryService.getPaperBrowserHistoryByUid(user.getUid());
        historyPageDTO.setPageSize(pageSize);
        historyPageDTO.setTotalPage(page.getPages());
        historyPageDTO.setContentList(paperBrowseHistoryList);
        request.getSession().setAttribute("historyPage", historyPageDTO);
        return "paperBrowseHistoryShow";
    }

}
