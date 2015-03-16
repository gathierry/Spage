package db;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

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
    private String duration; // 3, 6
    private String reference;
    private String link;
    private Date postDate;


    static final int PORT = 27017;
    static final String DB_NAME = "stageTestDB";
    static final String COLLECTION_NAME = "posts";
    static final String ID = "id";
    static final String SOURCE = "source";
    static final String TITLE = "title";
    static final String ENTERPRISE = "enterprise";
    static final String FIELD = "field";
    static final String BAC = "bac";
    static final String DURATION = "duration";
    static final String REFERENCE = "reference";
    static final String LINK = "link";
    static final String POST_DATE = "postDate";


    public Post (String id, String source, String title, String enterprise, String field, String bac, String duration, String reference, String link, Date postDate) {
        this.id = id;
        this.source = source;
        this.title = title;
        this.enterprise = enterprise;
        this.field = field;
        this.bac = bac;
        this.duration = duration;
        this.reference = reference;
        this.link = link;
        this.postDate = postDate;
    }

    public String getId() {
        return this.id;
    }

    public String getSource() {
        return this.source;
    }

    public String getTitle() {
        return this.title;
    }

    public String getEnterprise() {
        return this.enterprise;
    }

    public String getField() {
        return this.field;
    }

    public String getBac() {
        return this.bac;
    }

    public String getDuration() {
        return this.duration;
    }

    public String getReference() {
        return this.reference;
    }

    public String getLink() {
        return this.link;
    }

    public Date getPostDate() {
        return this.postDate;
    }

    private static MongoClient getMongoClient() throws UnknownHostException {
//        String host = System.getenv("OPENSHIFT_MONGODB_DB_HOST");
//        String sport = System.getenv("OPENSHIFT_MONGODB_DB_PORT");
//        String db = System.getenv("OPENSHIFT_APP_NAME");
//        if(db == null) db = "mydb";
//        String user = System.getenv("OPENSHIFT_MONGODB_DB_USERNAME");
//        String password = System.getenv("OPENSHIFT_MONGODB_DB_PASSWORD");
//        int port = Integer.decode(sport);
//
//        ServerAddress server = new ServerAddress(host, port);
//        MongoCredential credential = MongoCredential.createCredential(user, db, password.toCharArray());
//        MongoClient mongoClient = new MongoClient(server, Arrays.asList(credential));
//        return mongoClient;
        return new MongoClient( "localhost", PORT);
    }

    private static DB getDataBase(MongoClient mongoClient) {
        DB stagedb = mongoClient.getDB(DB_NAME);
        //DB stagedb = mongoClient.getDB(System.getenv("OPENSHIFT_APP_NAME"));
        return stagedb;
    }

    public boolean save() throws Exception{
        boolean success = false;
        MongoClient mongoClient = getMongoClient();
        DB stagedb = getDataBase(mongoClient);
        DBCollection postsCollection = stagedb.getCollection(COLLECTION_NAME);
        BasicDBObject postDBObject = new BasicDBObject(ID, this.id)
                .append(SOURCE, this.source)
                .append(TITLE, this.title)
                .append(ENTERPRISE, this.enterprise)
                .append(FIELD, this.field)
                .append(BAC, this.bac)
                .append(DURATION, this.duration)
                .append(REFERENCE, this.reference)
                .append(LINK, this.link)
                .append(POST_DATE, this.postDate);

        if(postsCollection.findOne(new BasicDBObject(ID, this.id)) == null) {
            postsCollection.insert(postDBObject);
            success = true;
        }
        mongoClient.close();
        return success;
    }

    static public ArrayList<Post> getList(String keywords, String bac, String duration) throws UnknownHostException {
        ArrayList<Post> list = new ArrayList<Post>();

        MongoClient mongoClient = getMongoClient();
        DB stagedb = getDataBase(mongoClient);
        DBCollection postsCollection = stagedb.getCollection(COLLECTION_NAME);

        String [] keys = keywords.split(" ");

        Pattern patternBac = Pattern.compile("(.*)" + bac +"(.*)", Pattern.CASE_INSENSITIVE);
        Pattern patternDuration = Pattern.compile("(.*)" + duration +"(.*)", Pattern.CASE_INSENSITIVE);

        ArrayList <BasicDBObject> searchList = new ArrayList<BasicDBObject>();
        for (String key : keys) {
            String[] fields = FieldGuesser.searchField(key);
            for (String f : fields) {
                Pattern patternKeywords = Pattern.compile("(.*)" + f + "(.*)", Pattern.CASE_INSENSITIVE);
                searchList.add(new BasicDBObject(TITLE, patternKeywords));
                searchList.add(new BasicDBObject(FIELD, patternKeywords));
                searchList.add(new BasicDBObject(ENTERPRISE, patternKeywords));
            }
        }

        BasicDBObject query = new BasicDBObject("$or", searchList)
                .append(BAC, patternBac)
                .append(DURATION, patternDuration);
        DBCursor cursor = postsCollection.find(query).limit(100).sort(new BasicDBObject(POST_DATE, -1).append(ENTERPRISE, 1));
        while (cursor.hasNext()) {
            BasicDBObject dbObject = (BasicDBObject) cursor.next();
            Post post = new Post(dbObject.getString(ID),
                    dbObject.getString(SOURCE),
                    dbObject.getString(TITLE),
                    dbObject.getString(ENTERPRISE),
                    dbObject.getString(FIELD),
                    dbObject.getString(BAC),
                    dbObject.getString(DURATION),
                    dbObject.getString(REFERENCE),
                    dbObject.getString(LINK),
                    dbObject.getDate(POST_DATE));
            list.add(post);
        }
        mongoClient.close();
        return list;
    }

    static public void removeHistory() throws UnknownHostException {
        MongoClient mongoClient = getMongoClient();
        DB stagedb = getDataBase(mongoClient);
        DBCollection postsCollection = stagedb.getCollection(COLLECTION_NAME);

        Date limitDate = new Date(System.currentTimeMillis() - (7 * 1000 * 86400));
        BasicDBObject query = new BasicDBObject(POST_DATE, limitDate);
        postsCollection.remove(query);

        mongoClient.close();
    }

    public String toString() {
        String str = ID +  " : " + this.id + "\n";
        str += SOURCE + " : " + this.source + "\n";
        str += TITLE + " : " + this.title + "\n";
        str += ENTERPRISE + " : " + this.enterprise + "\n";
        str += FIELD + " : " + this.field + "\n";
        str += BAC + " : " + this.bac + "\n";
        str += DURATION + " : " + this.duration + "\n";
        str += REFERENCE + " : " + this.reference + "\n";
        str += LINK + " : " + this.link + "\n";
        str += POST_DATE + " : " + this.postDate + "\n";
        return str;
    }

}
