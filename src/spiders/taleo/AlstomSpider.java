package spiders.taleo;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import db.Post;
import spiders.Analyser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        page = page.getHtmlElementById("advancedSearchFooterInterface.searchAction").click();

//        for (int i = 1; ;i ++) {
//
//        }
        HtmlPage detailPage = page.getHtmlElementById("requisitionListInterface.reqTitleLinkAction.row7").click();
        String postDate = page.getHtmlElementById("requisitionListInterface.reqPostingDate.row7").asText();
        analyzePage(detailPage, postDate);

        webClient.closeAllWindows();
    }

    static void analyzePage(HtmlPage page, String postDate) throws ParseException {
        String title = page.getHtmlElementById("requisitionDescriptionInterface.reqTitleLinkAction.row1").asText();
        String reference = page.getHtmlElementById("requisitionDescriptionInterface.reqContestNumberValue.row1").asText();
        String source = "ALSTOM";
        String id = source + "-" + reference;
        String enterprise = "Alstom";
        String field = page.getHtmlElementById("requisitionDescriptionInterface.ID1739.row1").asText();
        int bac = 0;
        int duration = Analyser.getDuration(page.getHtmlElementById("requisitionDescriptionInterface.ID3306.row.row1").asText());
        Date date = new SimpleDateFormat("MMM dd, yyyy").parse(postDate);

        Post post = new Post(id, source, title, enterprise, field, bac, duration, reference, date);
        System.out.print(post);
    }
}
