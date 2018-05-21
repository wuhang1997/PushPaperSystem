package cn.zjut.wangjie.pushpaper.pojo;

import lombok.Data;

/**
 * @program: pushpaper
 * @description: 推荐paper
 * @author: WangJie
 * @create: 2018-05-21 11:06
 **/
@Data
public class Recommend {
    private Integer id ;
    private Integer paperId;
    private Integer userId;
    private Long    addAt;
}
