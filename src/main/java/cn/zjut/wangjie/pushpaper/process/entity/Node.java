package cn.zjut.wangjie.pushpaper.process.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

/**
 * @program: pushpaper
 * @description:
 * @author: WangJie
 * @create: 2018-05-24 11:40
 **/
@Data
@NoArgsConstructor
public class Node {
    private String id;
    private String label;
    private double size;
    private double x;
    private double y;



}
