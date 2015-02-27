package db;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.*;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Thierry on 2/27/15.
 */
public class DBConnection {

    static public void startDb() throws Exception {
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        System.out.println(mongoClient.getDatabaseNames());
        DB db = mongoClient.getDB( "db" );
        System.out.println(db.getCollectionNames());
    }

}
