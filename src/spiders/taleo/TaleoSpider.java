package spiders.taleo;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import spiders.Spider;

/**
 * Created by Thierry on 2/9/15.
 */
public abstract class TaleoSpider extends Spider {

    public TaleoSpider (String url) throws Exception {
        super(url);
    }

    abstract public void crawlData() throws Exception;

    public HtmlPage taleoSearchPage(WebClient webClient) throws Exception {
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

        // result number per page (refresh page)
        HtmlSelect numSelect = page.getHtmlElementById("requisitionListInterface.dropListSize");
        HtmlOption numOp = numSelect.getOptionByText("100");
        page = numSelect.setSelectedAttribute(numOp, true);

        return page;
    }

}
