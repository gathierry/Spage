package spiders;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.Locale;

/**
 * Created by Thierry on 2/28/15.
 */
public class Analyser {

    static public String getDuration(String description) {

        int mIndex = description.toLowerCase(Locale.FRENCH).lastIndexOf("mois");
        int mIndex2 = mIndex + "mois".length();
        if (mIndex < 0) {
            mIndex = description.lastIndexOf("months");
            mIndex2 = mIndex + "months".length();
        }
        if (mIndex < 0) return "36";

        String neighbor = description.substring(mIndex - 2, mIndex - 1);
        if (NumberUtils.isNumber(neighbor)) {
            if (Integer.parseInt(neighbor) <= 3) {
                String min1 = new String();
                if(mIndex2 < description.length()-2) {
                    min1 = description.substring(mIndex2 + 1, mIndex2 + 2 + "minimum".length());
                }
                String min2 = description.substring(mIndex - "minimum".length() - 3, mIndex - 3);
                if (min1.startsWith("min") || min2.startsWith("min                                                                                                    ")) {
                    return "6";
                }
                else return "3";
            }
            else {
                return "6";
            }
        };
        return "36";
    }

    static public String getBac(String description) {
        String result = "0";
        description = description.replace(" ", "");
        description = description.toLowerCase();
        int index = 1;
        while (index > 0) {
            index = description.indexOf("bac", index + 1);
            if (index > 0) {
                if (description.substring(index + 3, index + 4).equals("+")){
                    String num = description.substring(index + 4, index + 5);
                    if (NumberUtils.isNumber(num)) result += num;
                }
            }
            else break;
        }
        if (result.equals("0")) result = "12345";
        return result;
    }
}
