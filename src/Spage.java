import com.gargoylesoftware.htmlunit.*;

public class Spage {
	
	public static void main(String[] args) throws Exception {
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);

//		EtudiantSpider eSpider = new EtudiantSpider();
//		eSpider.crawlData("informatique", "3", "ios android", 4);
        SchneiderSpider sSpider = new SchneiderSpider();
        sSpider.crawlData(webClient, "", "", "", 0);
	}

}
