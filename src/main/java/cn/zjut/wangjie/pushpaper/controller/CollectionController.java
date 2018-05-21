package cn.zjut.wangjie.pushpaper.controller;

import cn.zjut.wangjie.pushpaper.pojo.Collection;
import cn.zjut.wangjie.pushpaper.pojo.User;
import cn.zjut.wangjie.pushpaper.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
        collection.setAddAt(System.currentTimeMillis());
        collection.setPaperId(paperId);
        collection.setUserId(user.getUid());
        int result =collectionService.deleteCollection(collection);
        return result==1? "success": "fail";
    }
}
