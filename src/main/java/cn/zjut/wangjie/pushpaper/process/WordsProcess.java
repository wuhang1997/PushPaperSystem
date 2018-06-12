package cn.zjut.wangjie.pushpaper.process;

import cn.zjut.wangjie.pushpaper.process.entity.Edge;
import cn.zjut.wangjie.pushpaper.process.entity.Node;
import cn.zjut.wangjie.pushpaper.process.entity.Word;
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


    public static String getWordsDependency(String file){
        String wordsDependencyJson = new String();
        try {
            BufferedReader bf = new BufferedReader(new FileReader(file));
            wordsDependencyJson = bf.readLine();
            bf.close();
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        return wordsDependencyJson;
    }
    public static void writeWordsDependency(List<String>texts , String filePath){

        //每篇文档单词词频
        List<Map<String,Integer>> docs = new ArrayList<>(500);
        //List<Map<String,Word>>docs = new ArrayList<>(500);
        //每篇文档中词的个数
        Map<Map<String,Integer>,Integer> docWordsNum = new HashMap<>(500);

        //出现词的文档篇数
        Map<String , Integer> wordInDocsNum = new HashMap<>(5000);
        int totalWords = 0;
        //所有单词
        Set<String> allWords = new HashSet<>(100);
        Set<String> stopWords = StopWords();
        int docWords;
        for(String text : texts){
            String[] wordsArr1 = text.split(" ");
            List<String> words = new ArrayList<>(20);
            docWords = 0;
            for (String word : wordsArr1) {
                if(word.length() != 0){
                    if(!stopWords.contains(word.toUpperCase())) {

                        words.add(word);
                        allWords.add(word);
                        totalWords++;
                        docWords++;
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

            docWordsNum.put(wordsCount,docWords);


            docs.add(wordsCount);

        }

        int count ;
        for(String word : allWords){
            count = 0 ;
            for (Map m :docs){
                if (m.containsKey(word)){
                    count++;
                }
            }
            wordInDocsNum.put(word,count);
        }
        List<Map<String,Double>> wordsInDocsTFIDF =new ArrayList<>(500);



            for (Map<String,Integer> doc :docs){
                Map<String,Double> wordInOneDocTFIDF = new HashMap<>(100);
                for(String word : doc.keySet()){
                    double TF =( (double)doc.get(word) ) / ((double)docWordsNum.get(doc));

                    double IDF = Math.log(( (double)docs.size() )/(double)(wordInDocsNum.get(word)+1));
                    wordInOneDocTFIDF.put(word,TF*IDF);
                }
              wordsInDocsTFIDF.add(wordInOneDocTFIDF);
            }

           /* for(Map m : wordsInDocsTFIDF){
                System.out.println(m.toString());
            }*/


        List<Node> nodes = new ArrayList<>(1000);
        List<Edge> edges = new ArrayList<>(5000);
        Random random = new Random();
        Map<String ,Double> wordTFIDF = new HashMap<>(1000);
        for(String word: allWords){

            double totalTFIDF = 0;
            int i = 0;

            List<Edge> edgesForOneWord = new ArrayList<>(500);
            for (Map<String , Integer> doc : docs ){

                if (doc.containsKey(word)) {

                        totalTFIDF += wordsInDocsTFIDF.get(i).get(word);
                        for (String key : doc.keySet()) {
                            if (!word.equals(key)) {
                                Edge edge = new Edge();
                                edge.setSourceID(word);
                                edge.setTargetID(key);
                                edgesForOneWord.add(edge);
                            }
                        }

                }

                i++;
            }


            if (totalTFIDF>6) {
                Node node = new Node();
                node.setId(word);
                node.setLabel(word);
                node.setSize(totalTFIDF * 3);
                node.setX(random.nextDouble()*4000);
                node.setY(random.nextDouble()*4000);

                edges.addAll(edgesForOneWord);
                nodes.add(node);
            }

            wordTFIDF.put(word,totalTFIDF);


        }

        writeToTextFile(JSON.toJSONString(wordTFIDF),filePath+"wordTotalPDFIDF.json");
            List<Map.Entry<String,Double>> list = new ArrayList<Map.Entry<String,Double>>(wordTFIDF.entrySet());
            Collections.sort(list,new Comparator<Map.Entry<String,Double>>() {
                @Override
                //降序
                public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                    return o2.getValue().compareTo(o1.getValue());
                }


            });

        Map<String,Double> wordTotalDFIDFDesc = new HashMap<>(1000);
        int i = 0;
        for(Map.Entry<String,Double> mapping:list){

            wordTotalDFIDFDesc.put(mapping.getKey(),mapping.getValue());
            i++;
            if (i==100){
                break;
            }
        }
        writeToTextFile(JSON.toJSONString(wordTotalDFIDFDesc),filePath+"wordTotalDFIDFTop100.json");
        Map<String , Object> map = new HashMap<>(2);
        map.put("nodes",nodes);
        map.put("edges",edges);

        writeToTextFile(JSON.toJSONString(map),filePath+"wordsDependency.json");

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

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        return stopWords;
    }
    public static void writeToTextFile(String content ,String file) {

        try {



            BufferedWriter output = new BufferedWriter(new FileWriter(file));

            output.write(content);

            output.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}
