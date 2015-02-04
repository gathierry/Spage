import java.net.URL;
import java.util.List;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

public class EtudiantSpider extends Spider {

	public EtudiantSpider(String url) throws Exception {
		super(url);
	}

	public void crawlData(String[] fields, String duration, String keyword, int bac) throws Exception {
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
		URL url = this.targetUrl;
		HtmlPage page = webClient.getPage(url);
		
		List<DomElement> fsList = page.getElementsByTagName("fieldset");
		// first field set for keyword search
		HtmlElement searchInput = (HtmlTextInput)fsList.get(0).getElementsByTagName("input").get(0);
		searchInput.setTextContent(keyword);
		
		DomElement checkboxes = fsList.get(1);
		List<HtmlElement> ulList = checkboxes.getElementsByTagName("ul");
		// domaine
		List<HtmlElement> itemList0 = ulList.get(0).getHtmlElementsByTagName("li");
		List<HtmlElement> itemList1 = ulList.get(1).getHtmlElementsByTagName("li");
		itemList0.addAll(itemList1);
		for (String field : fields) {
			for (HtmlElement item : itemList0) {
				HtmlAnchor anchor = (HtmlAnchor)item.getHtmlElementsByTagName("label").get(0).getElementsByTagName("a").get(0);
				String anchorTitle = anchor.getAttribute("title");
				if (anchorTitle.indexOf(field) != -1) {
					((HtmlCheckBoxInput)item.getHtmlElementsByTagName("input").get(0)).setChecked(true);
				}
			}
		}
		//bac could be 3, 4, 5 or nothing
		List<HtmlElement> itemList11 = ulList.get(11).getHtmlElementsByTagName("li");
		((HtmlCheckBoxInput)itemList11.get(0).getHtmlElementsByTagName("input").get(0)).setChecked(true);
		((HtmlCheckBoxInput)itemList11.get(1).getHtmlElementsByTagName("input").get(0)).setChecked(true);
		if (bac == 3 || bac == 4) {
			((HtmlCheckBoxInput)itemList11.get(0).getHtmlElementsByTagName("input").get(0)).setChecked(false);
		}
		else if (bac == 5) ((HtmlCheckBoxInput)itemList11.get(1).getHtmlElementsByTagName("input").get(0)).setChecked(false);
		//click search
		page = ((HtmlElement)(page.getElementsByTagName("button").get(0))).click();
		
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
