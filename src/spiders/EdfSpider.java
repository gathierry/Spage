package spiders;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import db.Post;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Thierry on 3/5/15.
 */
public class EdfSpider extends Spider {

    public EdfSpider () throws Exception {
        super("http://www.edfjoinus.com/iframes.php?id_page=182");
    }

    public void crawlData() throws Exception {
        // Ignore unnecessary warning
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);

        // set web client
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(true);
        // webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setTimeout(35000);
        webClient.getOptions().setThrowExceptionOnScriptError(false);

        // get the page

        for (int t = 1; t < 3; t ++) {
            int days = 0;

            HtmlPage page = webClient.getPage("http://agregateur.kimladi.fr/widget/ZuU11CkBGoKc/serp/?languei=L0AQMr2uMGhZMg&langue=L0AQMr2uMGhZMg&job_type=rW%2F%2BMXDZgicCwrWUwiiK77cavg%3D%3D&candidate_educational_level=rW%2F%2BMXDZgicCwrXExSCBh94Y&job_company=&job_function=&location=rW%2F%2BMXDZgicCwrWowSuD&keyword=&se_sbt=Search&page=" + t);
            HtmlDivision sujDivsion = page.getHtmlElementById("serp_res_job_list");
            List<HtmlElement> anchorList = sujDivsion.getHtmlElementsByTagName("a");
            for (int i = 0; i < anchorList.size(); i ++ ){
                HtmlPage detailPage = anchorList.get(i).click(true, false, false);
                //title & reference
                HtmlDivision titleDiv = detailPage.getHtmlElementById("job_title");
                List<HtmlElement> pList = titleDiv.getElementsByTagName("p");
                String title = pList.get(0).asText();
                String reference = pList.get(1).asText();
                reference = reference.substring(1, reference.length() - 1);
                //duration & bac
                HtmlDivision jobDiv = detailPage.getHtmlElementById("job");
                String description = jobDiv.asText();
                String duration = Analyser.getDuration(title + description);
                String bac = Analyser.getBac(title + description);
                //field & post date
                String noSpaceDes = description.replace(" ", "");
                int a = noSpaceDes.indexOf("Areaofactivity:");
                int r = noSpaceDes.indexOf("Region:");
                String field = "";
                if (a > 0 && r > 0) {
                    field = noSpaceDes.substring(a + "Areaofactivity:".length(), r - 1);
                }

                int p = noSpaceDes.indexOf("Postedon:");
                int j = noSpaceDes.indexOf("Jobdescription");
                String postDate = "";
                Date date = new Date();
                if (p > 0 && j > 0) {
                    postDate = noSpaceDes.substring(p + "Postedon:".length(), j);
                }
                if (postDate.length() > 0) {
                    date = new SimpleDateFormat("MM/dd/yyyy", Locale.FRENCH).parse(postDate);
                    days = new Period(new DateTime(date), new DateTime(), PeriodType.days()).getDays();
                    if (days > 2) {
                        webClient.closeAllWindows();
                        return;
                    }
                }

                String source = "EDF";
                String id = source + "-" + reference;
                String enterprise = "EDF";
                Post post = new Post(id, source, title, enterprise, field, bac, duration, reference, detailPage.getUrl().toString(), date);
                System.out.println(post + "\n");
                post.save();
            }
        }
        webClient.closeAllWindows();
    }
}
