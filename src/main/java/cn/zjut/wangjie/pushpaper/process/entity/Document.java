package cn.zjut.wangjie.pushpaper.process.entity;

import lombok.Data;

/**
 * @program: pushpaper
 * @description:
 * @author: WangJie
 * @create: 2018-06-03 14:41
 **/
@Data
public class Document {
    private int id;
    private Word word;
    private int wordNum;

}
