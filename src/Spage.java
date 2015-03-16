import com.sun.javafx.scene.layout.region.Margins;
import db.Converter;
import spiders.EtudiantSpider;
import spiders.RteSpider;

public class Spage {

	public static void main(String[] args) throws Exception {
//        new EtudiantSpider().crawlData();
        String[] result = Converter.searchField("dfs");
        for(int i = 0;i<result.length;i++)
            System.out.println(result[i]);
    }



}
