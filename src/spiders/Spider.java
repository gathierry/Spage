package spiders;

import java.net.URL;

public abstract class Spider {
	
	public URL targetUrl;
	
	public Spider (String url) throws Exception {
		this.targetUrl = new URL(url);
	}
	
	abstract public void crawlData(String field, String duration, String keyword, int bac) throws Exception;
	

}
