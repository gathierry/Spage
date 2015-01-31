import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

import org.jsoup.Jsoup;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

public class Spage {

	public static void main(String[] args) throws Exception {
		// Ignore unnecessary warning
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);

		//Initiate a webclient
		final WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setCssEnabled(false);
		//webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setTimeout(35000);
		webClient.getOptions().setThrowExceptionOnScriptError(false);

		
		
		
		URL url = new URL("http://jobs-stages.letudiant.fr/stages-etudiants/offres/domaines-25/niveaux-2/regions-r3012874/page-1.html");
		HtmlPage page = webClient.getPage(url);
		HtmlTable table = (HtmlTable)page.getElementsByTagName("table").get(0);
		HtmlTableBody tableBody = table.getBodies().get(0);
		for (HtmlTableRow row : tableBody.getRows()) {
			for (HtmlTableCell cell : row.getCells()) {
				List<HtmlElement> anchorList = cell.getHtmlElementsByTagName("a");
				if (anchorList.size() > 0) {
					//System.out.println(((HtmlAnchor)anchorList.get(0)).getHrefAttribute());
					Page cellPage = (Page)anchorList.get(0).click();
					if (cellPage.isHtmlPage()) System.out.println(((HtmlPage)cellPage).asText());
				}
				
			}
		}
		
		
		
		
		
		File file = new File("file.html");
		if (file.exists())
			file.delete();
		//page.save(file);

		//System.out.println(page.asText());
		webClient.closeAllWindows();

	}

}
