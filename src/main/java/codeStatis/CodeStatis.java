package codeStatis;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: wAnG
 * @Date: 2022/6/22 17:21
 * @Description:
 */

public class CodeStatis {

    private static int lineCount = 0;

    private static boolean IsMultiLineStatue = false;

    private static final String readPath = "src/main/resources/StringUtils.java";

    private static final String writePath = "src/main/resources/validLineCount.txt";

    public static void main(String[] args) {
        List<String> lines = readFile();
        lineCount = lines.size();
        lines.forEach(line -> {
            if(IsMultiLineStatue || !singleLine(line)){
                multiLine(line);
            }
        });
        writeFile();
        System.out.println(lineCount);
    }

    public static void multiLine(String line){
        if(line.charAt(0) == '/'){
            lineCount--;
            IsMultiLineStatue = true;
        } else if((line.length() == 1 || line.charAt(1) != '/') && IsMultiLineStatue){
            lineCount--;
        } else if(line.charAt(0) == '*' && (line.length() == 1 || line.charAt(1) == '/')){
            lineCount--;
            IsMultiLineStatue = false;
        }
    }

    public static boolean singleLine(String line){
        if (line.charAt(0) == '/' && line.charAt(1) == '/'){
            lineCount--;
            return true;
        }
        return false;
    }
    public static void writeFile(){
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(writePath));
            writer.write(String.valueOf(lineCount));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static List<String> readFile(){
        List<String> list = new ArrayList<>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(readPath));
            String line = bufferedReader.readLine();
            while (line != null){
                line = line.trim();
                if(line.length() != 0){
                    list.add(line);
                }
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
