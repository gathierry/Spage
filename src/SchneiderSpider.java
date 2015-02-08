import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

import java.util.*;

/**
 * Created by Thierry on 2/8/15.
 */

public class SchneiderSpider extends TaleoSpider {

    static final private HashMap<String, String> fieldTable = new HashMap<String, String>(){{
        put("finance", "10870430105");
        put("informatique", "2170430105");
        put("technologies", "4870430105");
        put("logistique", "11970430105");
    }};

    public SchneiderSpider() throws Exception {
        super("https://schneiderele.taleo.net/careersection/2/moresearch.ftl?lang=fr&LOCATION=250370031531");
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
