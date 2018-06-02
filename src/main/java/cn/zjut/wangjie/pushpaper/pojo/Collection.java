package cn.zjut.wangjie.pushpaper.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: pushpaper
 * @description:
 * @author: WangJie
 * @create: 2018-05-10 17:18
 **/
@Data
@NoArgsConstructor
public class Collection {
    private Integer id;
    private Integer paperId;
    private String  article;
    private String  authors;
    private String  website;
    private Integer userId;
    private Long addAt;
}
