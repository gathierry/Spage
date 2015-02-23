import com.gargoylesoftware.htmlunit.*;

public class Spage {
	
	public static void main(String[] args) throws Exception {
//		EtudiantSpider eSpider = new EtudiantSpider();
//		eSpider.crawlData("informatique", "3", "ios android", 4);
        EtudiantSpider eSpider = new EtudiantSpider();
        eSpider.crawlData("informatique", "", "", 0);
//        AlstomSpider aSpider = new AlstomSpider();
//        aSpider.crawlData("informatique", "", "", 0);
        
	}

}
