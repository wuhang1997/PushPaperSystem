package cn.zjut.wangjie.pushpaper.service.elasticsearch.impl;

import cn.zjut.wangjie.pushpaper.mapper.ELPaperInfoRepository;
import cn.zjut.wangjie.pushpaper.pojo.PageDTO;
import cn.zjut.wangjie.pushpaper.pojo.PaperInfo;
import cn.zjut.wangjie.pushpaper.process.WordsProcess;
import cn.zjut.wangjie.pushpaper.service.elasticsearch.ELPaperService;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;

import io.searchbox.core.Index;
import io.searchbox.core.Search;
import lombok.extern.java.Log;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * @program: PushPaper
 * @description:
 * @author: WangJie
 * @create: 2018-05-05 18:59
 **/
@Service
@Log
@Transactional
public class ELPaperServiceImpl implements ELPaperService {
    @Autowired
    private JestClient jestClient;

    @Autowired
    private ELPaperInfoRepository elPaperInfoRepository;
    private static final String INDEX_NAME = "index_paper_push";

    private static final String TYPE = "paperInfo";

    @Override
    public void savePaperInfo(PaperInfo paperInfo) {
        elPaperInfoRepository.save(paperInfo);
    }


    @Override
    public List<PaperInfo> searchPaperInfo(String searchStr) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery().queryName(searchStr));
        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(INDEX_NAME).addType(TYPE).build();

        log.info(searchSourceBuilder.toString());
        try {
            JestResult jestResult = jestClient.execute(search);
            return jestResult.getSourceAsObjectList(PaperInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
            log.info("向elasticsearch查询数据出错，插入内容为：" + searchStr);
        }
        return null;
    }

    @Override
    public PageDTO searchPaperInfoListByPage(PageDTO pageDTO, String searchContent) {


        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .should((QueryBuilders.matchQuery("article", searchContent)))
                .should(QueryBuilders.matchQuery("authors", searchContent))
                .should(QueryBuilders.matchQuery("paperAbstract", searchContent));

        Iterable<PaperInfo> paperInfoIterable = elPaperInfoRepository.search(boolQueryBuilder);
        Iterator<PaperInfo> iterator = paperInfoIterable.iterator();
        List<PaperInfo> paperInfoList = new ArrayList<>(16);


        while (iterator.hasNext()) {

            paperInfoList.add(iterator.next());

        }

        if (paperInfoList.size() == 0) {
            pageDTO.setTotalPage(0);
            pageDTO.setContentList(null);
            return null;
        }

        pageDTO.calculatBegin();


        int totalPages = paperInfoList.size() % pageDTO.getPageSize() > 0 ? paperInfoList.size() / pageDTO.getPageSize() + 1 : paperInfoList.size() / pageDTO.getPageSize();
        pageDTO.setTotalPage(totalPages);
        pageDTO.calculatBegin();
        int begin = pageDTO.getBegin();
        int end = pageDTO.getBegin() + pageDTO.getPageSize();
        if (end > paperInfoList.size() - 1) {
            end = paperInfoList.size() - 1;
        }
        if (begin > paperInfoList.size() - 1) {
            begin = paperInfoList.size() - 1;
        }
        pageDTO.setContentList(paperInfoList.subList(begin, end));

        return pageDTO;
    }

    @Override
    public List<PaperInfo> recommend(List<String> searchContentList, List<Integer> collectionPaperIds, List<Integer> recommendedPaperIds) {
        // String[] texts = {" Lerrel Pinto, James Davidson, Rahul Sukthankar, Abhinav Gupta ; "," Alexander Kolesnikov, Christoph H. Lampert ;"};
        String[] fields = {"article", "authors", "paperAbstract"};

        int size = collectionPaperIds.size();

        MoreLikeThisQueryBuilder.Item[] collectionItems = new MoreLikeThisQueryBuilder.Item[size];
        for (int i = 0; i < size; i++) {
            collectionItems[i] = new MoreLikeThisQueryBuilder.Item("index_paper_push", "paperInfo", collectionPaperIds.get(i).toString());
        }
        String[] texts = new String[searchContentList.size()];
        MoreLikeThisQueryBuilder moreLikeThisQueryBuilder;
        texts = searchContentList.toArray(texts);


        if (collectionPaperIds.size() > 0) {
            moreLikeThisQueryBuilder =
                    QueryBuilders.moreLikeThisQuery(fields, texts, collectionItems);
        }else {
            moreLikeThisQueryBuilder =
                    QueryBuilders.moreLikeThisQuery(texts);
        }



      /*  size = recommendedPaperIds.size();
        MoreLikeThisQueryBuilder.Item[] recommendedItems = new MoreLikeThisQueryBuilder.Item[size];
        for(int i = 0 ;i<size ; i++){
            recommendedItems[i] = new MoreLikeThisQueryBuilder.Item("index_paper_push","paperInfo",recommendedPaperIds.get(i).toString());
        }
        moreLikeThisQueryBuilder.unlike(recommendedItems);
      */
        Set<String> stopWords = WordsProcess.StopWords();

        String[] words = new String[stopWords.size()];
        int i = 0;
        for (String word : stopWords
                ) {
            words[i] = word;
            i++;
        }
        moreLikeThisQueryBuilder.stopWords(words);
        moreLikeThisQueryBuilder.minDocFreq(2);
        moreLikeThisQueryBuilder.maxDocFreq(80);
        //moreLikeThisQueryBuilder.minTermFreq(3);
        //moreLikeThisQueryBuilder.maxQueryTerms(10);
        moreLikeThisQueryBuilder.minimumShouldMatch("60%");

        log.info(moreLikeThisQueryBuilder.toString());
        Iterable<PaperInfo> paperInfoIterable = elPaperInfoRepository.search(moreLikeThisQueryBuilder);


        Iterator<PaperInfo> iterator = paperInfoIterable.iterator();
        List<PaperInfo> paperInfoList = new ArrayList<>(16);


        PaperInfo paperInfo;
        while (iterator.hasNext()) {

            paperInfo = iterator.next();
            boolean has = false;
            for (Integer id : recommendedPaperIds) {
                if (paperInfo.getPaperId().equals(id)) {
                    has = true;
                    break;
                }
            }
            if (!has) {
                paperInfoList.add(paperInfo);
            }


        }


        return paperInfoList;
    }
}
