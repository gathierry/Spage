import java.net.URL;

public abstract class Spider {
	
	URL targetUrl;
	
	public Spider (String url) throws Exception {
		this.targetUrl = new URL(url);
	}
	
	abstract public void crawlData(String[] fields, String duration, String keyword, int bac) throws Exception;
	

}
