package cn.zjut.wangjie.pushpaper.process;

import cn.zjut.wangjie.pushpaper.process.entity.Edge;
import cn.zjut.wangjie.pushpaper.process.entity.Node;
import com.alibaba.fastjson.JSON;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.*;

/**
 * @program: pushpaper
 * @description: 词分析
 * @author: WangJie
 * @create: 2018-05-24 11:06
 **/
public class WordsProcess {


    public static String getWordsDependency(){
        String wordsDependencyJson = new String();
        Resource resource = new ClassPathResource("wordsDependency.json");

        try {
            File file = resource.getFile();
            BufferedReader bf = new BufferedReader(new FileReader(file));
            wordsDependencyJson = bf.readLine();
            bf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return wordsDependencyJson;
    }
    public static void writeWordsDependency(List<String>texts){

        List<Map<String,Integer>> docs = new ArrayList<>(500);
        int totalWords = 0;
        //所有单词
        Set<String> allWords = new HashSet<>(100);
        Set<String> stopWords = StopWords();
        for(String text : texts){
            String[] wordsArr1 = text.split(" ");
            List<String> words = new ArrayList<>(20);
            for (String word : wordsArr1) {
                if(word.length() != 0){
                    if(!stopWords.contains(word.toUpperCase())) {

                        words.add(word);
                        allWords.add(word);
                        totalWords++;
                    }
                }
            }
            //存储单词计数信息，key值为单词，value为单词数
            Map<String, Integer> wordsCount = new TreeMap<String,Integer>();
            //单词的词频统计
            for (String li : words) {
                if(wordsCount.get(li) != null){
                    wordsCount.put(li,wordsCount.get(li) + 1);
                }else{
                    wordsCount.put(li,1);
                }

            }
        /*    //删除词频小于2的词
            Iterator entryIterator = wordsCount.entrySet().iterator();
            while(entryIterator.hasNext()) {
                Map.Entry<String,Integer> entry = (Map.Entry<String,Integer>) entryIterator.next();
                if((Integer)entry.getValue()<10) {
                    entryIterator.remove();
                }
            }*/


            docs.add(wordsCount);

        }

        List<Node> nodes = new ArrayList<>(100);
        List<Edge> edges = new ArrayList<>(500);
        for(String word: allWords){
            Node node = new Node();
            node.setId(word);
            node.setLabel(word);
            double count = 0;
            for (Map<String , Integer> doc : docs ){

                if (doc.containsKey(word)){
                    count += doc.get(word);
                    for (String key : doc.keySet()){
                        if (!word.equals(key)){
                            Edge edge = new Edge();
                            edge.setSourceID(word);
                            edge.setTargetID(key);
                            edges.add(edge);
                        }
                    }
                }
            }
            node.setSize((count/totalWords)*5000);
            if (node.getSize()>100){
                System.out.println(node.getId()+"  "+node.getSize());
            }

            nodes.add(node);


        }

        Map<String , Object> map = new HashMap<>(2);
        map.put("nodes",nodes);
        map.put("edges",edges);

        writeToTextFile(JSON.toJSONString(map));

    }
    public static Set<String> StopWords() {
        Set stopWords = new HashSet<String>();
        Resource resource = new ClassPathResource("stop_word.txt");

        try {
            File file = resource.getFile();
            BufferedReader bf = new BufferedReader(new FileReader(file));
            String line = null;
            while((line=bf.readLine())!=null) {
                stopWords.add(line.trim().toUpperCase());
            }
            bf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return stopWords;
    }
    public static void writeToTextFile(String content) {

        try {

            Resource resource = new ClassPathResource("wordsDependency.json");


            File file = resource.getFile();


            if (!file.exists()) {

                file.createNewFile();

            }



            BufferedWriter output = new BufferedWriter(new FileWriter(file));

            output.write(content);

            output.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}
