import db.Converter;

public class Spage {

	public static void main(String[] args) throws Exception {
        String[] result = Converter.searchField("dfs");
        for(int i = 0;i<result.length;i++)
            System.out.println(result[i]);
    }



}
