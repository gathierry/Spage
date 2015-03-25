package spiders.taleo;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import db.Post;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import spiders.Analyser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Thierry on 2/27/15.
 */
public class RenaultSpider extends TaleoSpider {

    public RenaultSpider() throws Exception {
        super("https://tas-renault.taleo.net/careersection/5/moresearch.ftl?lang=fr_fr");
    }

    public void crawlData() throws Exception {

        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        HtmlPage page = this.taleoSearchPage(webClient);

        // select location
        HtmlSelect locSelect = page.getHtmlElementById("advancedSearchInterface.location1L1");
        HtmlOption locOp = locSelect.getOptionByText("France");
        locSelect.setSelectedAttribute(locOp, true);

        // check intern
        HtmlCheckBoxInput internCheckBoxInput = (HtmlCheckBoxInput)page.getHtmlElementById("advancedSearchInterface.jobtype_2check");
        internCheckBoxInput.setChecked(true);

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
                this.analyzePage(detailPage, postDates.get(i));
            }
        }

        webClient.closeAllWindows();
    }

    private boolean analyzePage(HtmlPage page, Date postDate) throws Exception {
        String title = page.getHtmlElementById("requisitionDescriptionInterface.reqTitleLinkAction.row1").asText();
        String reference = page.getHtmlElementById("requisitionDescriptionInterface.reqContestNumberValue.row1").asText();
        String source = "RENAULT";
        String id = source + "-" + reference;
        String enterprise = "Renault";
        String field = page.getHtmlElementById("requisitionDescriptionInterface.ID1678.row1").asText();
        String description = page.getHtmlElementById("requisitionDescriptionInterface.ID3119.row.row1").asText();
        String duration = Analyser.getDuration(title + description);
        String bac = Analyser.getBac(title + description);

        Post post = new Post(id, source, title, enterprise, field, bac, duration, reference, this.targetUrl.toString(), postDate);
        System.out.print(post + "\n");
        return post.save();
    }

}
