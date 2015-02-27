package spiders.taleo;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

import java.util.HashMap;

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
        //HtmlPage detailPage = page.getHtmlElementById("requisitionListInterface.reqTitleLinkAction.row1").click();


        System.out.println(page.asText());

        webClient.closeAllWindows();
    }

}
