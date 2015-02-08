import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

import java.util.*;

/**
 * Created by Thierry on 2/9/15.
 */
public class AlstomSpider extends TaleoSpider {

    static final private HashMap<String, String> fieldTable = new HashMap<String, String>(){{
        put("finance", "3870450175");
        put("informatique", "5370450175");
        put("technologies", "29170114043");
        put("logistique", "5470450175");
    }};

    public AlstomSpider() throws Exception {
        super("https://alstom.taleo.net/careersection/2/moresearch.ftl");
    }

    public HtmlPage crawlData(WebClient webClient, String field, String duration, String keyword, int bac) throws Exception {

        HtmlPage page = super.crawlData(webClient, field, duration, keyword, bac);

        HtmlPage detailPage = page.getHtmlElementById("requisitionListInterface.reqTitleLinkAction.row1").click();
        HtmlTableRow row =  detailPage.getHtmlElementById("requisitionDescriptionInterface.ID3143.row.row1");

        System.out.println(row.asText());

        webClient.closeAllWindows();

        return page;
    }
}
