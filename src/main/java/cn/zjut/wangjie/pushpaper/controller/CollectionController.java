package cn.zjut.wangjie.pushpaper.controller;

import cn.zjut.wangjie.pushpaper.pojo.Collection;
import cn.zjut.wangjie.pushpaper.pojo.PageDTO;
import cn.zjut.wangjie.pushpaper.pojo.PaperInfo;
import cn.zjut.wangjie.pushpaper.pojo.User;
import cn.zjut.wangjie.pushpaper.service.CollectionService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: pushpaper
 * @description:
 * @author: WangJie
 * @create: 2018-05-10 17:50
 **/
@Controller
@RequestMapping("/paper-collection")
public class CollectionController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private CollectionService collectionService;
    @Value("${page.pageSize}")
    private int pageSize;
    @PostMapping(value = "/add")
    @ResponseBody
    public String addCollection (Integer paperId){
        User user = (User)request.getSession().getAttribute("user");
        Collection collection = new Collection();
        collection.setAddAt(System.currentTimeMillis());
        collection.setPaperId(paperId);
        collection.setUserId(user.getUid());
        collection.setAddAt(System.currentTimeMillis());
        int result =collectionService.addCollection(collection);
        return result==1? "success": "fail";
    }
    @PostMapping("/delete")
    @ResponseBody
    public String deleteCollection(Integer paperId){
        User user = (User)request.getSession().getAttribute("user");
        Collection collection = new Collection();
        collection.setPaperId(paperId);
        collection.setUserId(user.getUid());
        int result =collectionService.deleteCollection(collection);
        return result==1? "success": "fail";
    }

    @GetMapping("/show")
    public String collectionsShow( @RequestParam(value="currentPage") Integer currentPage ){
        PageDTO collectionPageDTO = (PageDTO)request.getSession().getAttribute("collectionPage");
        if(collectionPageDTO==null) {
            collectionPageDTO = new PageDTO();
            collectionPageDTO.setPageSize(pageSize);
        }
        if (currentPage == null){
            currentPage = 1;
        }
        collectionPageDTO.setCurrentPage(currentPage);
        User user = (User)request.getSession().getAttribute("user");
        Page page = PageHelper.startPage(currentPage,pageSize);
        List<PaperInfo> paperInfoList = collectionService.getCollectionsByUid(user.getUid());
        collectionPageDTO.setPageSize(pageSize);
        collectionPageDTO.setTotalPage(page.getPages());
        collectionPageDTO.setContentList(paperInfoList);
        request.getSession().setAttribute("collectionPage", collectionPageDTO);
        return "collectionShow";
    }

}
