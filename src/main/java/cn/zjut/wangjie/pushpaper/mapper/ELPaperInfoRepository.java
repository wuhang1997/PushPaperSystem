package cn.zjut.wangjie.pushpaper.mapper;


import cn.zjut.wangjie.pushpaper.pojo.PaperInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @program: PushPaper
 * @description: elasticsearch的dao
 * @author: WangJie
 * @create: 2018-05-05 21:05
 **/
@Repository
public interface ELPaperInfoRepository extends ElasticsearchRepository<PaperInfo,Integer>  {
    Page<PaperInfo> findByArticleIsLikeOrAuthorsIsLikeOrPaperAbstractIsLike(String searchContent , Pageable pageable);
    //Page<PaperInfo> findByArticle(String searchContent , Pageable pageable);
    List<PaperInfo> findByArticle(String searchContent);
    List<PaperInfo> findPaperInfoByArticleIsLike();
    @Query("{\"bool\" : {\"must\" : {\"term\" : {\"article\" : \"?0\"}}}}")
    List<PaperInfo> querytttByArticle(String searchContent);
}
