package spiders;

import java.net.URL;
import java.util.HashMap;
import java.util.List;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

public class EtudiantSpider extends Spider {

// http://jobs-stages.letudiant.fr/stages-etudiants/offres/libelle_libre-ios%20iphone/domaines-25_28/niveaux-2/page-1.html	
	
	@SuppressWarnings("serial")
	static final private HashMap<String, String> fieldTable = new HashMap<String, String>(){{
		put("finance", "18");
		put("informatique", "25");
		put("technologies", "28");
		put("logistique", "31");
	}};
	
	public EtudiantSpider() throws Exception {
		super("http://jobs-stages.letudiant.fr/stages-etudiants");
	}

    public void crawlData(String field, String duration, String keyword, int bac) throws Exception {
		//make url
		String newUrl = this.targetUrl.toString() + "/offres/";
		if (keyword.length() > 0) newUrl += "libelle_libre-" + keyword + "/";
		if (field.length() > 0) newUrl += "domaines-" + fieldTable.get(field) + "/";
		if (bac == 3 || bac == 4) newUrl += "niveaux-2/";
		else if (bac == 5) newUrl += "niveaux-1/";
		URL url = new URL(newUrl + "page-1.html");
		System.out.println(url);
		
		// Ignore unnecessary warning
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);

		// set web client
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setCssEnabled(false);
		// webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setTimeout(35000);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		
		// get the page
		HtmlPage page = webClient.getPage(url);
		//System.out.println(page.getUrl());

		analyseTable(page);

		webClient.closeAllWindows();
	}

    private static void analyseTable(HtmlPage page) throws Exception{
        // table of the posts
        HtmlTable table = (HtmlTable)page.getElementsByTagName("table").get(0);
        HtmlTableBody tableBody = table.getBodies().get(0);
        for (HtmlTableRow row : tableBody.getRows()) {
            for (HtmlTableCell cell : row.getCells()) {
                List<HtmlElement> anchorList = cell.getHtmlElementsByTagName("a");
                if (anchorList.size() > 0) {
                    Page cellPage = (Page)anchorList.get(0).click();
                    if (cellPage.isHtmlPage()) {
                        HtmlDivision div = (HtmlDivision)((HtmlPage) cellPage).getHtmlElementById("content-color");
//                        System.out.println("\n\n\n___________________________" + cellPage.getUrl() + "\n\n\n");
//                        System.out.println(div.asText());
                        DomElement div1 = ((HtmlPage) cellPage).getElementsByTagName("h1").get(0);
                        System.out.println(div1.asText() );
                        div1 = ((HtmlPage) cellPage).getElementsByTagName("h2").get(0);
                        String divContent = div1.asText();
                        String[] divContentSplited = divContent.split("\\|");
                        for(int j = 0;j<divContentSplited.length;j++)
                        {
                        	divContentSplited[j].replace(" ","");
                        	System.out.println(divContentSplited[j]);
                        }
                        
                        DomElement div2 = ((HtmlPage) cellPage).getElementById("content-color");
                        divContent = div2.asText();
                        int FirstIndex = divContent.indexOf("P¨¦riode");
                        int LastIndex = divContent.indexOf("R¨¦mun¨¦ration");
                        if(FirstIndex > 0 && LastIndex > 0 && LastIndex > FirstIndex)
                            System.out.println(divContent.substring(FirstIndex, LastIndex));
                        System.out.println("______" + cellPage.getUrl() + "\n\n\n");

                    }

                }

            }
        }
    }
}