package cn.zjut.wangjie.pushpaper.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: pushpaper
 * @description: 修改文件某行内容
 * @author: WangJie
 * @create: 2018-05-19 12:33
 **/
public class ModifyFileUtil {
    public static void modifyFile(String file , String content ,String replace){
        try {
            // 读取文本文件
            List<String> lines = Files.readAllLines(Paths.get(file));
            for (int i = 0; i < lines.size(); i++) {

                if (content.equals(lines.get(i))) {
                    lines.remove(i);
                    lines.add(i, replace);
                }
            }

            Files.write(Paths.get(file), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
