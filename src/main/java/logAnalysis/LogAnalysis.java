package logAnalysis;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: wAnG
 * @Date: 2022/6/21 16:39
 */

public class LogAnalysis {

    public static int getCount = 0;

    public static int postCount = 0;

    public static Map<String,Integer> URICount = new HashMap<>();

    public static Map<String, Set<String>> mergeMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        List<String> logs = readFile();
        System.out.println("请求总量:");
        System.out.println(logs.size());
        logs.forEach(line -> {
            String URI = methodCount(line);
            URICountAndMerge(URI);
        });
        calculate();
    }

    public static void calculate(){
        System.out.println("Get数量:");
        System.out.println(getCount);
        System.out.println("Post数量:");
        System.out.println(postCount);
        List<String> Top10URI = URICount.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        List<Integer> Top10Count = URICount.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(10)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        System.out.println("求最频繁的 10 个 HTTP 接口:");
        for (int i = 0; i < Top10URI.size();i++){
            System.out.println("URI: " + Top10URI.get(i) + " - " + "数量：" + Top10Count.get(i));
        }
        System.out.println("分类");
        mergeMap.forEach((key,value) -> {
            System.out.println(key + ":");
            value.forEach(System.out::println);
            System.out.println("===============================");
        });
    }

    public static void URICountAndMerge(String URI){
        String HTTPUri = Lists.newArrayList(Splitter.on("?").split(URI)).get(0);
        URICount.merge(HTTPUri, 1, Integer::sum);
        List<String> list = Lists.newArrayList(Splitter.on("/").limit(2).omitEmptyStrings().split(HTTPUri));
        String prefix = list.get(0);
        String suffix = null;
        if(list.size() == 1){
            suffix = "";
        }else {
            suffix = list.get(1);
        }
        Set<String> suffixSet = mergeMap.get(prefix);
        if(suffixSet == null){
            HashSet<String> set = new HashSet<>();
            set.add(suffix);
            mergeMap.put(prefix,set);
        }else {
            suffixSet.add(suffix);
            mergeMap.put(prefix,suffixSet);
        }
    }

    public static String methodCount(String line){
        ArrayList<String> list = Lists.newArrayList(Splitter.on(" ").split(line));
        String method = list.get(0);
        if(method.equals("GET")){
            getCount++;
        }else {
            postCount++;
        }
        return list.get(1);
    }

    public static List<String> readFile(){
        FileInputStream fileInputStream = null;
        StringBuilder stringBuilder = null;
        List<String> list = new ArrayList<>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("src/main/resources/access.log"));
            String line = bufferedReader.readLine();
            while (line != null){
                list.add(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
}
