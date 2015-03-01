package spiders.taleo;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import db.Post;
import spiders.Analyser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

        // check bac
        if (bac == 3) {
            HtmlCheckBoxInput bacCheckBoxInput = (HtmlCheckBoxInput)page.getHtmlElementById("advancedSearchInterface.studyLevel_9check");
            bacCheckBoxInput.setChecked(true);
        }
        if (bac == 4) {
            HtmlCheckBoxInput bacCheckBoxInput = (HtmlCheckBoxInput)page.getHtmlElementById("advancedSearchInterface.studyLevel_5check");
            bacCheckBoxInput.setChecked(true);
        }
        if (bac == 5) {
            HtmlCheckBoxInput bacCheckBoxInput = (HtmlCheckBoxInput)page.getHtmlElementById("advancedSearchInterface.studyLevel_6check");
            bacCheckBoxInput.setChecked(true);
        }


        page = page.getHtmlElementById("advancedSearchFooterInterface.searchAction").click();

//        for (int i = 1; ;i ++) {
//
//        }
        HtmlPage detailPage = page.getHtmlElementById("requisitionListInterface.reqTitleLinkAction.row2").click();
        String postDate = page.getHtmlElementById("requisitionListInterface.reqPostingDate.row2").asText();
        analyzePage(detailPage, postDate, bac);

        webClient.closeAllWindows();
    }

    static void analyzePage(HtmlPage page, String postDate, int b) throws ParseException {
        String title = page.getHtmlElementById("requisitionDescriptionInterface.reqTitleLinkAction.row1").asText();
        String reference = page.getHtmlElementById("requisitionDescriptionInterface.reqContestNumberValue.row1").asText();
        String source = "RENAULT";
        String id = source + "-" + reference;
        String enterprise = "Renault";
        String field = page.getHtmlElementById("requisitionDescriptionInterface.ID1678.row1").asText();
        int bac = b;
        int duration = Analyser.getDuration(page.getHtmlElementById("requisitionDescriptionInterface.ID3119.row.row1").asText());
        Date date = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH).parse(postDate);

        Post post = new Post(id, source, title, enterprise, field, bac, duration, reference, date);
        System.out.print(post);
    }

}
