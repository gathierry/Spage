import db.Post;

public class Spage {

	public static void main(String[] args) throws Exception {
        System.out.println(Post.getList("finance informatique", "", ""));
    }



}
