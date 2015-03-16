package db;

import java.util.ArrayList;

/**
 * Created by Whenever Smile on 2015/3/16.
 */

public class FieldGuesser {

    static String[] fields = new String[]{"statistique",
            "automatique automatisation automation contrôle optimisation R&D amélioration",
            "logiciel informatique exploitation IT networkoperation Dévelopement",
            "technologie transport mechanisme industrie R&D",
            "civil social",
            "qualité contrôle",
            "réseau web IT",
            "design",
            "maitenance méthode",
            "engineering ingénerie",
            "méchanisme industrie engineering",
            "finance commercial commerce vente",
            "management administration Ressources RH H/R R&H clientèle gestion"
};
    static int number = fields.length;

    static public String[] searchField(String ent)
    {
        ArrayList<String> fieldCorres = new ArrayList<String>();
        boolean find = false;
        for(int i = 0;i<number;i++)
            if (fields[i].indexOf(ent) >= 0) {
                String[] result = fields[i].split(" ");
                for(int j = 0;j<result.length;j++) {
                    if(!fieldCorres.contains(result[j]))
                    {
                        fieldCorres.add(result[j]);
                    }

                }

                find = true;
            }
        if(find)
        {
            String[] resultFinal = fieldCorres.toArray(new String[fieldCorres.size()]);
            return resultFinal;
        }
        else
        {
            String[] resultOriginal = new String[1];
            resultOriginal[0] = ent;
            return resultOriginal;
        }

    }
}
