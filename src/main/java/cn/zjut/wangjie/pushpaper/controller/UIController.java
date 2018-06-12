package cn.zjut.wangjie.pushpaper.controller;

import cn.zjut.wangjie.pushpaper.pojo.PaperInfo;
import cn.zjut.wangjie.pushpaper.process.WordsProcess;
import cn.zjut.wangjie.pushpaper.service.PaperService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @program: pushpaper
 * @description:
 * @author: WangJie
 * @create: 2018-05-24 13:02
 **/
@Controller
public class UIController {
    @Autowired
    private PaperService paperService;
    @Value("${paperPath}")
    private String paperPath;
    @GetMapping("/main")
    @ResponseBody
    public String getWordsDependcy() {
        List<String> paperArticleList = paperService.getAllPaperArticle();


        return WordsProcess.getWordsDependency(paperPath+"wordsDependency.json");
    }

    @GetMapping("/wordCloud")
    @ResponseBody
    public String getWordsCloud(){
        return WordsProcess.getWordsDependency(paperPath+"wordTotalDFIDFTop100.json");
    }
}
