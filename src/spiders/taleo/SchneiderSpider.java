package spiders.taleo;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import db.Post;
import spiders.Analyser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Thierry on 2/8/15.
 */

public class SchneiderSpider extends TaleoSpider {

    public SchneiderSpider() throws Exception {
        super("https://schneiderele.taleo.net/careersection/2/moresearch.ftl?lang=en&LOCATION=250370031531", new HashMap<String, String>(){{
            put("finance", "10870430105");
            put("informatique", "2170430105");
            put("technologies", "4870430105");
            put("logistique", "11970430105");
        }});

    }

    public void crawlData(String field, String duration, String keyword, int bac) throws Exception {

        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        HtmlPage page = this.taleoSearchPage(webClient, field, duration, keyword, bac);
        page = page.getHtmlElementById("advancedSearchFooterInterface.searchAction").click();


        //        for (int i = 1; ;i ++) {
//
//        }

        HtmlPage detailPage = page.getHtmlElementById("requisitionListInterface.reqTitleLinkAction.row2").click();
        String postDate = page.getHtmlElementById("requisitionListInterface.reqPostingDate.row2").asText();
        analyzePage(detailPage, postDate);

        webClient.closeAllWindows();
    }

    static void analyzePage(HtmlPage page, String postDate) throws ParseException {
        String title = page.getHtmlElementById("requisitionDescriptionInterface.reqTitleLinkAction.row1").asText();
        String reference = page.getHtmlElementById("requisitionDescriptionInterface.reqContestNumberValue.row1").asText();
        String source = "SCHNEIDERELE";
        String id = source + "-" + reference;
        String enterprise = "Schneider Electric";
        String field = "";
        int bac = 0;
        int duration = Analyser.getDuration(page.getHtmlElementById("requisitionDescriptionInterface.ID3157.row.row1").asText());
        Date date = new SimpleDateFormat("MMM dd, yyyy").parse(postDate);

        Post post = new Post(id, source, title, enterprise, field, bac, duration, reference, date);
        System.out.print(post);
    }
}
