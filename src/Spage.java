import db.Post;
import spiders.Analyser;
import spiders.EtudiantSpider;
import spiders.taleo.*;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class Spage {
	
	public static void main(String[] args) throws Exception {
//        EtudiantSpider eSpider = new EtudiantSpider();
//        eSpider.crawlData("informatique", "", "", 0);
//        AlstomSpider aSpider = new AlstomSpider();
//        aSpider.crawlData("", "", "", 4);
//        RenaultSpider rSpider = new RenaultSpider();
//        rSpider.crawlData("", "", "", 4);
        String str = "Environnement de travail (interlocuteurs, rattachement hiérarchique) :\n" +
                "Rattaché(e) au manager Véhicules d’occasion, Remarketing et Valeurs résiduelles, il/elle sera associé(e)  aux différents comités qui encadrent l'activité (econf avec les pays, comités de pilotage, comité de suivi avec les structures corporateetc.).\n" +
                " \n" +
                "Apports du stage :\n" +
                "Partie intégrante de l’équipe le/la stagiaire participe aux évènements organisés par la direction, ce qui lui permettra d’avoir une vision globale de la ventes aux entreprises sur un périmètre Européen\n" +
                " \n" +
                "Qualifications\n" +
                " \n" +
                "niveau de pré requis:\n" +
                "Lieu du Stage:\n" +
                "Le Plessis Robinson (92),\n" +
                " \n" +
                "Connaissances requises (logiciels, langues,...) :\n" +
                "La maîtrise de l'anglais est indispensable\n" +
                "Maîtrise de Microsoft Office nécessaire, et d’excel indispensable\n" +
                " \n" +
                "Qualités attendues :\n" +
                "- Ouverture d'esprit et dynamisme, esprit critique, capacités analytiques et de synthèse.\n" +
                "- Motivé, rigoureux, ayant le goût de l'automobile, et ayant envie d'apprendre un métier du commerce.\n" +
                " \n" +
                "Catégorie métier\n" +
                " \n" +
                "Vente\n";
        System.out.println(Analyser.getBac(str));


	}

}
