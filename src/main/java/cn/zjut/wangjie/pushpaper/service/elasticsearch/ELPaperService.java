package cn.zjut.wangjie.pushpaper.service.elasticsearch;

import cn.zjut.wangjie.pushpaper.pojo.PageDTO;
import cn.zjut.wangjie.pushpaper.pojo.PaperInfo;

import java.util.List;

/**
 * @program: PushPaper
 * @description: elasticsearch中操作paper
 * @author: WangJie
 * @create: 2018-05-05 18:56
 **/
public interface ELPaperService {
    void savePaperInfo(PaperInfo paperInfo);
    List<PaperInfo> searchPaperInfo(String searchStr);
    PageDTO searchPaperInfoListByPage(PageDTO pageDTO,String searchContent);
    List<PaperInfo> recommend(List<String> texts,List<Integer>paperIds,List<Integer> recommendedPaperIds);

}
