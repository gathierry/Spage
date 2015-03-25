package spiders;

/**
 * Created by Thierry on 3/6/15.
 */


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import db.Post;

import java.util.Date;
import java.util.List;

/**
 * Created by Thierry on 3/5/15.
 */
public class RteSpider extends Spider {

    public RteSpider () throws Exception {
        super("http://www.rte-france.com/fr/public/candidat");
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
            HtmlPage page = webClient.getPage("http://agregateur.kimladi.fr/widget/ZuU11CkBCeiZn9eucl0VTA/serp/?job_type=rW%2F%2BMXDZgicCwrXExSCBh9wfuZo%3D&candidate_educational_level=&job_category=&location_state=&keyword=&se_sbt=Rechercher&page=" + t);
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
                int a = noSpaceDes.indexOf("Familledemétiers:");
                int r = noSpaceDes.indexOf("Région(s):");
                String field = "";
                if (a > 0 && r > 0) {
                    field = noSpaceDes.substring(a + "Familledemétiers:".length(), r - 1);
                }
                Date date = new Date(System.currentTimeMillis() - (1000 * 86400));

                String source = "RTE";
                String id = source + "-" + reference;
                String enterprise = "RTE";
                Post post = new Post(id, source, title, enterprise, field, bac, duration, reference, detailPage.getUrl().toString(), date);
                System.out.println(post + "\n");
                if (!post.save()) {
                    webClient.closeAllWindows();
                    return;
                }
                System.out.println("___");
            }
        }
        webClient.closeAllWindows();
    }
}

