package cn.zjut.wangjie.pushpaper.pojo;

import lombok.Data;

/**
 * @program: pushpaper
 * @description:
 * @author: WangJie
 * @create: 2018-05-24 16:46
 **/
@Data
public class PaperBrowseHistory {
    private Integer id ;
    private Integer paperId;
    private Integer userId;
    private Long    addAt;
    private String  article;
    private String  authors;
    private String  website;
}
