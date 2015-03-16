import db.FieldGuesser;

public class Spage {

	public static void main(String[] args) throws Exception {
        String[] result = FieldGuesser.searchField("R&D");
        for(int i = 0;i<result.length;i++)
        {
            System.out.println(result[i]);
        }
        //System.out.println(Post.getList("finance informatique", "", ""));
    }



}
