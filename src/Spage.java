import spiders.taleo.AlstomSpider;
import spiders.taleo.RenaultSpider;
import spiders.taleo.SchneiderSpider;

public class Spage {
	
	public static void main(String[] args) throws Exception {
//        EtudiantSpider eSpider = new EtudiantSpider();
//        eSpider.crawlData("informatique", "", "", 0);
//        AlstomSpider aSpider = new AlstomSpider();
//        aSpider.crawlData("", "", "", 4);
        SchneiderSpider sSpider = new SchneiderSpider();
        sSpider.crawlData("", "", "", 0);
//        RenaultSpider rSpider = new RenaultSpider();
//        rSpider.crawlData("", "", "", 4);

	}

}
