package cn.zjut.wangjie.pushpaper.pojo;

import lombok.Data;

/**
 * @program: pushpaper
 * @description: 评论
 * @author: WangJie
 * @create: 2018-05-29 11:20
 **/
@Data
public class Comment {
    private Integer id;
    private Integer paperId;
    private Integer userId;
    private Long addAt;
    private String content;
}
