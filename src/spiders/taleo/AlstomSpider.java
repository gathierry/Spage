package spiders.taleo;

import java.text.*;
import java.util.*;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import org.joda.time.*;

import db.Post;
import spiders.Analyser;

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

        Date postDate = new Date();
        int days = 0;
        ArrayList<Date> postDates = new ArrayList<Date>();
        for (int i = 1; ; i ++){
            postDate = new SimpleDateFormat("MMM dd, yyyy",Locale.ENGLISH).parse(page.getHtmlElementById("requisitionListInterface.reqPostingDate.row" + i).asText());
            days = new Period(new DateTime(postDate), new DateTime(), PeriodType.days()).getDays();
            if (days < 5) postDates.add(postDate);
            else break;
        }
        // have to update pages by clicking next
        if (postDates.size() > 0) {
            HtmlPage detailPage = page.getHtmlElementById("requisitionListInterface.reqTitleLinkAction.row1").click();
            analyzePage(detailPage, postDates.get(0));
            for (int i = 1; i < postDates.size(); i ++) {
                detailPage = detailPage.getHtmlElementById("requisitionDescriptionInterface.pagerDivID868.Next").click();
                analyzePage(detailPage, postDates.get(i));
            }
        }

        webClient.closeAllWindows();
    }

    static void analyzePage(HtmlPage page, Date postDate) throws Exception {
        String title = page.getHtmlElementById("requisitionDescriptionInterface.reqTitleLinkAction.row1").asText();
        String reference = page.getHtmlElementById("requisitionDescriptionInterface.reqContestNumberValue.row1").asText();
        String source = "ALSTOM";
        String id = source + "-" + reference;
        String enterprise = "Alstom";
        String field = page.getHtmlElementById("requisitionDescriptionInterface.ID1739.row1").asText();
        String description = page.getHtmlElementById("requisitionDescriptionInterface.ID3306.row.row1").asText();
        int duration = Analyser.getDuration(description);
        String bac = Analyser.getBac(description);

        Post post = new Post(id, source, title, enterprise, field, bac, duration, reference, postDate);
        post.save();

        System.out.print(post + "\n");
    }
}
