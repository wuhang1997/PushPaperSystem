package cn.zjut.wangjie.pushpaper.process.entity;

import lombok.Data;

/**
 * @program: pushpaper
 * @description:
 * @author: WangJie
 * @create: 2018-06-03 14:35
 **/
@Data
public class Word {
    private String name;
    private int num;
    private double tf;
    private double idf;

    public Word(){
        num = 0;
        tf = 0;
        idf = 0;
    }
    public double getTFIDF(){
        return tf*idf;
    }
}
