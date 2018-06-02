package cn.zjut.wangjie.pushpaper.controller;

import cn.zjut.wangjie.pushpaper.pojo.PageDTO;
import cn.zjut.wangjie.pushpaper.pojo.PaperBrowseHistory;
import cn.zjut.wangjie.pushpaper.pojo.Recommend;
import cn.zjut.wangjie.pushpaper.pojo.User;
import cn.zjut.wangjie.pushpaper.service.RecommendService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: pushpaper
 * @description:
 * @author: WangJie
 * @create: 2018-06-02 21:46
 **/
@Controller
@RequestMapping("/recommend")
public class RecommendController {

    @Autowired
    private RecommendService recommendService;
    @Autowired
    private HttpServletRequest request;
    @Value("${page.pageSize}")
    private int pageSize;
    @RequestMapping("/list-recommend")
    public String getRecommends(@RequestParam(value="currentPage") Integer currentPage ){
        PageDTO recommendPageDTO = (PageDTO)request.getSession().getAttribute("recommendPage");
        if(recommendPageDTO==null) {
            recommendPageDTO = new PageDTO();
            recommendPageDTO.setPageSize(pageSize);
        }
        if (currentPage == null){
            currentPage = 1;
        }
        recommendPageDTO.setCurrentPage(currentPage);
        User user = (User)request.getSession().getAttribute("user");
        Page page = PageHelper.startPage(currentPage,pageSize);
        List<Recommend> recommends = recommendService.listRecommendByUserId(user.getUid());

        recommendPageDTO.setTotalPage(page.getPages());
        recommendPageDTO.setContentList(recommends);
        request.getSession().setAttribute("recommendPage", recommendPageDTO);
        return "recommendShow";
    }


}
