import spiders.taleo.AlstomSpider;
import spiders.taleo.SchneiderSpider;

public class Spage {
	
	public static void main(String[] args) throws Exception {
//		spiders.EtudiantSpider eSpider = new spiders.EtudiantSpider();
//		eSpider.crawlData("informatique", "3", "ios android", 4);
//        EtudiantSpider eSpider = new EtudiantSpider();
//        eSpider.crawlData("informatique", "", "", 0);
        AlstomSpider aSpider = new AlstomSpider();
<<<<<<< HEAD
        SchneiderSpider sSpider = new SchneiderSpider();
        sSpider.crawlData("", "", "", 0);
=======
        aSpider.crawlData("", "", "", 4);
        
>>>>>>> FETCH_HEAD
	}

}
