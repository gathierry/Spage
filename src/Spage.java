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
        RenaultSpider rSpider = new RenaultSpider();
        rSpider.crawlData("", "", "", 4);


	}

}
