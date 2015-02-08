import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.net.URL;

public abstract class Spider {
	
	URL targetUrl;
	
	public Spider (String url) throws Exception {
		this.targetUrl = new URL(url);
	}
	
	abstract public HtmlPage crawlData(WebClient webClient, String field, String duration, String keyword, int bac) throws Exception;
	

}
