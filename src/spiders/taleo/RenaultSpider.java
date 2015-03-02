package spiders.taleo;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import db.Post;
import org.joda.time.*;
import spiders.Analyser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Thierry on 2/27/15.
 */
public class RenaultSpider extends TaleoSpider {

    public RenaultSpider() throws Exception {
        super("https://tas-renault.taleo.net/careersection/5/moresearch.ftl?lang=fr_fr", new HashMap<String, String>(){{
            put("finance", "20201430233");
            put("informatique", "24201430233");
            put("technologies", "26101430233");
            put("logistique", "46101430233");
        }});
    }

    public void crawlData(String field, String duration, String keyword, int bac) throws Exception {

        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        HtmlPage page = this.taleoSearchPage(webClient, field, duration, keyword, bac);

        // check intern
        HtmlCheckBoxInput internCheckBoxInput = (HtmlCheckBoxInput)page.getHtmlElementById("advancedSearchInterface.jobtype_1check");
        internCheckBoxInput.setChecked(false);
        internCheckBoxInput = (HtmlCheckBoxInput)page.getHtmlElementById("advancedSearchInterface.jobtype_2check");
        internCheckBoxInput.setChecked(true);

//        // check bac
//        if (bac == 3) {
//            HtmlCheckBoxInput bacCheckBoxInput = (HtmlCheckBoxInput)page.getHtmlElementById("advancedSearchInterface.studyLevel_9check");
//            bacCheckBoxInput.setChecked(true);
//        }
//        if (bac == 4) {
//            HtmlCheckBoxInput bacCheckBoxInput = (HtmlCheckBoxInput)page.getHtmlElementById("advancedSearchInterface.studyLevel_5check");
//            bacCheckBoxInput.setChecked(true);
//        }
//        if (bac == 5) {
//            HtmlCheckBoxInput bacCheckBoxInput = (HtmlCheckBoxInput)page.getHtmlElementById("advancedSearchInterface.studyLevel_6check");
//            bacCheckBoxInput.setChecked(true);
//        }


        page = page.getHtmlElementById("advancedSearchFooterInterface.searchAction").click();

        Date postDate = new Date();
        int days = 0;
        ArrayList<Date> postDates = new ArrayList<Date>();
        for (int i = 1; ; i ++){
            postDate = new SimpleDateFormat("dd MMMM yyyy",Locale.FRENCH).parse(page.getHtmlElementById("requisitionListInterface.reqPostingDate.row" + i).asText());
            days = new Period(new DateTime(postDate), new DateTime(), PeriodType.days()).getDays();
            if (days < 2) postDates.add(postDate);
            else break;
        }
        // have to update pages by clicking next
        if (postDates.size() > 0) {
            HtmlPage detailPage = page.getHtmlElementById("requisitionListInterface.reqTitleLinkAction.row1").click();
            analyzePage(detailPage, postDates.get(0));
            for (int i = 1; i < postDates.size(); i ++) {
                detailPage = detailPage.getHtmlElementById("requisitionDescriptionInterface.pagerDivID753.Next").click();
                analyzePage(detailPage, postDates.get(i));
            }
        }

        webClient.closeAllWindows();
    }

    static void analyzePage(HtmlPage page, Date postDate) throws Exception {
        String title = page.getHtmlElementById("requisitionDescriptionInterface.reqTitleLinkAction.row1").asText();
        String reference = page.getHtmlElementById("requisitionDescriptionInterface.reqContestNumberValue.row1").asText();
        String source = "RENAULT";
        String id = source + "-" + reference;
        String enterprise = "Renault";
        String field = page.getHtmlElementById("requisitionDescriptionInterface.ID1678.row1").asText();
        int bac = 0;
        int duration = Analyser.getDuration(page.getHtmlElementById("requisitionDescriptionInterface.ID3119.row.row1").asText());
        
        Post post = new Post(id, source, title, enterprise, field, bac, duration, reference, postDate);
        post.save();

        System.out.print(post + "\n");
    }

}
