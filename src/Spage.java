import spiders.taleo.AlstomSpider;
import spiders.taleo.SchneiderSpider;

public class Spage {
	
	public static void main(String[] args) throws Exception {
//		spiders.EtudiantSpider eSpider = new spiders.EtudiantSpider();
//		eSpider.crawlData("informatique", "3", "ios android", 4);
        AlstomSpider aSpider = new AlstomSpider();
        SchneiderSpider sSpider = new SchneiderSpider();
        sSpider.crawlData("", "", "", 0);
	}

}
