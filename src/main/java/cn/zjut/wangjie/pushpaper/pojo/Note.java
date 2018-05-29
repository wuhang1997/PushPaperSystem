package cn.zjut.wangjie.pushpaper.pojo;

import lombok.Data;

/**
 * @program: pushpaper
 * @description: 笔记
 * @author: WangJie
 * @create: 2018-05-29 11:21
 **/
@Data
public class Note {
    private Integer id;
    private Integer paperId;
    private Integer userId;
    private String content;
    private Long addAt;
    private Long updateAt;
}
