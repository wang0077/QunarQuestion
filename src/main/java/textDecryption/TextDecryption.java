package textDecryption;
import com.google.common.base.Splitter;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: wAnG
 * @Date: 2022/6/22 03:35
 * @Description:
 */
public class TextDecryption {

    private static final String templatePath = "src/main/resources/sdxl_template.txt";

    private static final String test = "src/main/resources/Test.txt";

    private static final String propPath = "src/main/resources/sdxl_prop.txt";

    private static final String writePath = "src/main/resources/ sdxl.txt";

    private static final String NATURAL_ORDER = "natureOrder";

    private static final String INDEX_ORDER = "indexOrder";

    private static final String CHAR_ORDER = "charOrder";

    private static final String CHAR_ORDER_DESC = "charOrderDESC";

    private static final List<String> templateText = new ArrayList<>();

    private static final String[] indexOrder = new String[6110];

    private static final List<String> natureOrder = new ArrayList<>();

    private static final List<String> charOrder= new ArrayList<>();

    private static BufferedWriter writer = null;

    public static void main(String[] args) {
//        Stopwatch started = Stopwatch.createStarted();
        readFile();
//        System.out.println(natureOrder.size());
//        long elapsed = started.elapsed(TimeUnit.MILLISECONDS);
//        System.out.println(elapsed);

        try {
            writer = new BufferedWriter(new FileWriter(writePath));
            templateText.forEach(TextDecryption::replace);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void replace(String line){
        int start = -1;
        while((start = line.indexOf('$')) != -1){
            int leftParenthese = line.indexOf("(");
            int rightParenthese = line.indexOf(")");
            int number = Integer.parseInt(line.substring(leftParenthese + 1, rightParenthese));
            String type = line.substring(start + 1, leftParenthese);
            String target = line.substring(start, rightParenthese + 1);
            line = chooseAndReplace(number,type,target,line);
        }
        try {
            writer.write(line + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String chooseAndReplace(int number,String type,String target,String line){
        if(type.equals(NATURAL_ORDER)){
            return line.replace(target,natureOrder.get(number));
        }else if (type.equals(INDEX_ORDER)){
            return line.replace(target,indexOrder[number]);
        }else if(type.equals(CHAR_ORDER)){
            return line.replace(target,charOrder.get(number));
        }else {
            return line.replace(target,charOrder.get(charOrder.size() - number - 1));
        }
    }


    public static void readFile(){
       readTemplateFile();
       readPropFile();
    }

    public static void readPropFile(){
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(propPath));
            String line = bufferedReader.readLine();
            while (line != null){
                ArrayList<String> segment = Lists.newArrayList(Splitter.on("\t").split(line));

                int number = Integer.parseInt(segment.get(0));
                String text = segment.get(1);
                indexOrder[number] = text;
                natureOrder.add(text);
                charOrder.add(text);
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
        Collections.sort(charOrder);
    }

    public static void readTemplateFile(){
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(templatePath));
            String line = bufferedReader.readLine();
            while (line != null){
                templateText.add(line);
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
    }
}