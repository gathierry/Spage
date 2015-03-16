package spiders;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class EtudiantSpider extends Spider {

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

    public void crawlData() throws Exception {
		//make url
		String newUrl = this.targetUrl.toString() + "/offres/";

        newUrl += "regions-r3038033_r3037350_r3035876_r3034693_r3030967_r3030293_r3027939_r3027257_r3023519_r1000001_r3017372_r3013756_r3012874_r3007670_r2998268_r2997551_r2993955_r2990119_r2988289_r2987375_r2986492_r2985244_r2983751/";
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
                        // title
                        DomElement div1 = ((HtmlPage) cellPage).getElementsByTagName("h1").get(0);
                        String title = div1.asText();
                        System.out.println("title "+title);

                        div1 = ((HtmlPage) cellPage).getElementsByTagName("h2").get(0);
                        String divContent = div1.asText();
                        String[] divContentSplited = divContent.split("\\|");
                        // employeur
                        String enterprise = cutString(divContentSplited[0].replace(" ",""), "Employeur:", "(autresoffres");
//                        System.out.println("enterprise "+enterprise);
//
//
//                        for(int j = 0;j < divContentSplited.length;j++)
//                        {
//                        	System.out.println(j + " - " + divContentSplited[j].replace(" ",""));
//                        }
//                        String source = "Etudiant";


                        DomElement div2 = ((HtmlPage) cellPage).getElementById("content-color");
                        divContent = div2.asText();
                        divContent = cutString(divContent, "Période", "Rémunération");
                        System.out.println("discription originale " + divContent);

                        String duration = Analyser.getDuration(divContent);

                        System.out.println("duration " + duration);
                        System.out.println("lien " + cellPage.getUrl() + "\n\n\n");


                        //Post post = new Post(...);
                        //posts.add(post);
                    }

                }

            }
        }
    }

    static String cutString(String originString, String start, String end) {
        int firstIndex = originString.indexOf(start) + start.length();
        int lastIndex = originString.indexOf(end);
        if(firstIndex > 0 && lastIndex > 0 && lastIndex > firstIndex){
            return originString.substring(firstIndex, lastIndex);
        }
        return originString;
    }
}