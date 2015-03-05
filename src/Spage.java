import spiders.taleo.AlstomSpider;
import spiders.taleo.RenaultSpider;

public class Spage {

	public static void main(String[] args) throws Exception {
        new AlstomSpider().crawlData();
        new RenaultSpider().crawlData();
    }



}
