package spiders;

import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.io.FileWriter;

/**
 * Created by Thierry on 2/28/15.
 */
public class Analyser {

    static public int getDuration(String description) {

        int mIndex = description.lastIndexOf("mois");
        int mIndex2 = mIndex + "mois".length();
        if (mIndex < 0) {
            mIndex = description.lastIndexOf("months");
            mIndex2 = mIndex + "months".length();
        }
        if (mIndex < 0) return 0;

        String neighbor = description.substring(mIndex - 2, mIndex - 1);
        if (NumberUtils.isNumber(neighbor)) {
            if (Integer.parseInt(neighbor) <= 3) {
                String min1 = description.substring(mIndex2 + 1, mIndex2 + 2 + "minimum".length());
                String min2 = description.substring(mIndex - "minimum".length() - 3, mIndex - 3);
                if (min1.startsWith("min") || min2.startsWith("min")) {
                    return 6;
                }
                else return 3;
            }
            else {
                return 6;
            }
        };
        return 0;
    }

    static public String getBac(String description) {
        String result = "0";
        description = description.replace(" ", "");
        int index = 1;
        while (index > 0) {
            index = findBac(description, index + 1);
            if (index > 0) {
                if (description.substring(index + 3, index + 4).equals("+")){
                    String num = description.substring(index + 4, index + 5);
                    if (NumberUtils.isNumber(num)) result += num;
                }
            }
            else break;
        }
        return result;
    }

    static private int findBac(String string, int index) {
        int bac = string.indexOf("bac", index);
        if (bac < 0) {
            bac = string.indexOf("Bac", index);
            if (bac < 0) {
                bac = string.indexOf("BAC", index);
            }
        }
        return bac;
    }

    public static void test(String fileName, String fileString) throws Exception{

        String writerContent = fileString;// 要写入的文本
        File file = new File(fileName);// 要写入的文本文件
        if (!file.exists()) {// 如果文件不存在，则创建该文件
            file.createNewFile();
        }
        FileWriter writer = new FileWriter(file);// 获取该文件的输出流
        writer.write(writerContent);// 写内容
        writer.flush();// 清空缓冲区，立即将输出流里的内容写到文件里
        writer.close();// 关闭输出流，施放资源
    }

}
