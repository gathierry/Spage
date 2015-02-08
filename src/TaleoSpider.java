import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

import java.net.URL;
import java.util.HashMap;

/**
 * Created by Thierry on 2/9/15.
 */
public class TaleoSpider extends Spider {

    static final private HashMap<String, String> fieldTable = new HashMap<String, String>();

    public TaleoSpider (String url) throws Exception {
        super(url);
    }

    public HtmlPage crawlData(WebClient webClient, String field, String duration, String keyword, int bac) throws Exception {
        // Ignore unnecessary warning
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);

        // set web client
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(true);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setTimeout(35000);
        webClient.getOptions().setThrowExceptionOnScriptError(false);

        // get the page
        HtmlPage page = webClient.getPage(this.targetUrl);

        // check intern
        HtmlCheckBoxInput internCheckBoxInput = (HtmlCheckBoxInput)page.getHtmlElementById("advancedSearchInterface.jobtype_1check");
        internCheckBoxInput.setChecked(true);

        // select location
        HtmlSelect locSelect = page.getHtmlElementById("advancedSearchInterface.location1L1");
        HtmlOption locOp = locSelect.getOptionByText("France");

        //key word
        if (keyword.length() > 0) {
            HtmlTextInput keywordInput = (HtmlTextInput)page.getHtmlElementById("advancedSearchInterface.keywordInput");
            keywordInput.setText(keyword);
        }

        //field
        if (field.length() > 0) {
            HtmlSelect fieldSelect = (HtmlSelect)page.getHtmlElementById("advancedSearchInterface.jobfield1L1");
            HtmlOption op = fieldSelect.getOptionByValue(fieldTable.get(field));
            fieldSelect.setSelectedAttribute(op, true);
        }

        page = page.getHtmlElementById("advancedSearchFooterInterface.searchAction").click();

        return page;
    }

}
