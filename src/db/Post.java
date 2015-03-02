package db;

import java.util.Date;
import com.mongodb.*;

/**
 * Created by Thierry on 2/28/15.
 */
public class Post {

    private String id;
    private String source;
    private String title;
    private String enterprise;
    private String field;
    private String bac; //"345"
    private int duration; // 3, 6
    private String reference;
    private Date postDate;

    public Post (String id, String source, String title, String enterprise, String field, String bac, int duration, String reference, Date postDate) {
        this.id = id;
        this.source = source;
        this.title = title;
        this.enterprise = enterprise;
        this.field = field;
        this.bac = bac;
        this.duration = duration;
        this.reference = reference;
        this.postDate = postDate;
    }

    public void save() throws Exception{
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        //DB stagedb = mongoClient.getDB("stageDB");
        DB stagedb = mongoClient.getDB("stageTestDB");
        DBCollection postsCollection = stagedb.getCollection("posts");

        BasicDBObject postDBObject = new BasicDBObject("id", this.id)
                .append("source", this.source)
                .append("title", this.title)
                .append("enterprise", this.enterprise)
                .append("field", this.field)
                .append("bac", this.bac)
                .append("duration", this.duration)
                .append("reference", this.reference)
                .append("postDate", this.postDate);

        if(postsCollection.findOne(new BasicDBObject("id", this.id)) == null) {
            postsCollection.insert(postDBObject);
        }

        mongoClient.close();
    }

    public String toString() {
        String str = "id : " + this.id + "\n";
        str += "source : " + this.source + "\n";
        str += "title : " + this.title + "\n";
        str += "enterprise : " + this.enterprise + "\n";
        str += "field : " + this.field + "\n";
        str += "bac : " + this.bac + "\n";
        str += "duration : " + this.duration + "\n";
        str += "reference : " + this.reference + "\n";
        str += "postDate : " + this.postDate + "\n";
        return str;
    }

}
