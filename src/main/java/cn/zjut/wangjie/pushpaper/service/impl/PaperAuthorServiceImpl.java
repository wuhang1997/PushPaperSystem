package cn.zjut.wangjie.pushpaper.service.impl;

import cn.zjut.wangjie.pushpaper.mapper.AuthorDao;
import cn.zjut.wangjie.pushpaper.mapper.PaperAuthorDao;
import cn.zjut.wangjie.pushpaper.pojo.Author;
import cn.zjut.wangjie.pushpaper.pojo.PaperAuthor;
import cn.zjut.wangjie.pushpaper.pojo.PaperInfo;
import cn.zjut.wangjie.pushpaper.service.PaperAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: pushpaper
 * @description:
 * @author: WangJie
 * @create: 2018-05-29 13:30
 **/
@Service
public class PaperAuthorServiceImpl implements PaperAuthorService {
    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private PaperAuthorDao paperAuthorDao;

    @Override
    public void addPaperAuthor(PaperInfo paperInfo) {


        String authorsStr = paperInfo.getAuthors();
        authorsStr.trim();
        authorsStr = authorsStr.replace(" ; ", ",");

        String[] authors = authorsStr.split(",");
        for(int i = 0 ; i< authors.length ; i++){
            Author author = authorDao.getAuthorByName(authors[i].trim());
            if (author == null){
                author = new Author();
                author.setName(authors[i].trim());
                authorDao.addAuthor(author);
            }
            PaperAuthor paperAuthor = paperAuthorDao.getPaperAuthorByAuthorIdAndPaperId(author.getAuthorId(),paperInfo.getPaperId());
            if (paperAuthor == null){
                paperAuthorDao.addPaperAuthor(author.getAuthorId(),paperInfo.getPaperId());
            }

        }

    }
}
