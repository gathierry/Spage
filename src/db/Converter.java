package db;

import java.util.ArrayList;

/**
 * Created by Whenever Smile on 2015/3/16.
 */

public class Converter {

    static String[] fields = new String[]{"statistique",
            "automatique automatisation automation contrôle optimisation",
            "logiciel informatique exploitation IT",
            "technologie transport mechanisme industrie",
            "civil social",
            "méchanisme industrie",
            "finance commercial commerce management",
            "management administration RH H/R R&H clientèle gestion"
};
    static int number = fields.length;
    public Converter()
    {

    }
    static public String[] searchField(String ent)
    {
        ArrayList<String> fieldCorres = new ArrayList<String>();
        boolean find = false;
        for(int i = 0;i<number;i++)
        {


            if(fields[i].indexOf(ent) >= 0)
            {
                String[] result = fields[i].split(" ");
                find = true;
                return result;
            }

        }
        String[] resultOriginal = new String[1];
        resultOriginal[0] = ent;
        return resultOriginal;

    }
}
