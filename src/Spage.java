
public class Spage {
	
	public static void main(String[] args) throws Exception {
		EtudiantSpider eSpider = new EtudiantSpider();
		eSpider.crawlData(new String[] {"Informatique", "Technologies"}, "3", "ios android", 4);

	}

}
