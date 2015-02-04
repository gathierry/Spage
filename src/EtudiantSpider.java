import java.net.URL;
import java.util.HashMap;
import java.util.List;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

public class EtudiantSpider extends Spider {

// http://jobs-stages.letudiant.fr/stages-etudiants/offres/libelle_libre-ios%20iphone/domaines-25_28/niveaux-2/page-1.html	
	
	@SuppressWarnings("serial")
	static final private HashMap<String, String> fieldTable = new HashMap<String, String>(){{
		put("Finance", "18");
		put("Informatique", "25");
		put("Technologies", "28");
		put("Logistique", "31");
	}};
	
	public EtudiantSpider() throws Exception {
		super("http://jobs-stages.letudiant.fr/stages-etudiants");
	}

	public void crawlData(String[] fields, String duration, String keyword, int bac) throws Exception {
		//make url
		String newUrl = this.targetUrl.toString() + "/offres/";
		if (keyword.length() > 0) newUrl += "libelle_libre-" + keyword + "/";
		if (fields.length > 0) {
			newUrl += "domaines-" + fieldTable.get(fields[0]);
			if (fields.length > 1) {
				for (int i = 1; i < fields.length; i ++) {
					newUrl += "_" + fieldTable.get(fields[i]);
				}
			}
			newUrl += "/";
		}
		if (bac == 3 || bac == 4) newUrl += "niveaux-2/";
		else if (bac == 5) newUrl += "niveaux-1/";
		URL url = new URL(newUrl + "page-1.html");
		
		// Ignore unnecessary warning
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);

		// Initiate a webclient
		final WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setCssEnabled(false);
		// webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setTimeout(35000);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		
		// get the page
		HtmlPage page = webClient.getPage(url);
		// table of the posts
		HtmlTable table = (HtmlTable)page.getElementsByTagName("table").get(0);
		HtmlTableBody tableBody = table.getBodies().get(0);
		for (HtmlTableRow row : tableBody.getRows()) {
			for (HtmlTableCell cell : row.getCells()) {
				List<HtmlElement> anchorList = cell.getHtmlElementsByTagName("a");
				if (anchorList.size() > 0) {
					Page cellPage = (Page)anchorList.get(0).click();
					if (cellPage.isHtmlPage()) System.out.println(((HtmlPage)cellPage).asText());
				}
				
			}
		}
		
		webClient.closeAllWindows();
	}
}




