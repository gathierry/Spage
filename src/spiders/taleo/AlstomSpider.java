package spiders.taleo;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

import java.util.*;

/**
 * Created by Thierry on 2/9/15.
 */
public class AlstomSpider extends TaleoSpider {

    private HashMap<String, String> fieldTable;

    public AlstomSpider() throws Exception {
        super("https://alstom.taleo.net/careersection/2/moresearch.ftl?lang=en", new HashMap<String, String>(){{
            put("finance", "3870450175");
            put("informatique", "5370450175");
            put("technologies", "29170114043");
            put("logistique", "5470450175");
        }});
    }

    public void crawlData(String field, String duration, String keyword, int bac) throws Exception {

        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        HtmlPage page = this.taleoSearchPage(webClient, field, duration, keyword, bac);

        HtmlPage detailPage = page.getHtmlElementById("requisitionListInterface.reqTitleLinkAction.row2").click();

        System.out.println(page.asText());

        webClient.closeAllWindows();
    }
}
