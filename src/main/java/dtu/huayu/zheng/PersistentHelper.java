package dtu.huayu.zheng;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.org.lightcouch.CouchDbException;

public class PersistentHelper {

    private static CloudantClient cloudant = null;
    private static Database db = null;

    private static String user = null;
    private static String password = null;
    private static String accountName = null;

    private static void initClient() {
        if (cloudant == null) {
            synchronized (PersistentHelper.class) {
                if (cloudant != null) {
                    return;
                }
                cloudant = createClient();
            } // end synchronized
        }
    }

    private static CloudantClient createClient() {
        try {
            System.out.println("Connecting to Cloudant : " + accountName);
            CloudantClient client = ClientBuilder.account(accountName)
                    .username(user)
                    .password(password)
                    .build();
            return client;
        } catch (CouchDbException e) {
            throw new RuntimeException("Unable to connect to repository", e);
        }
    }

    public static Database getDB(String userName, String password, String databaseName, String accountName) {
        if (cloudant == null) {
            user = userName;
            PersistentHelper.password = password;
            PersistentHelper.accountName = accountName;
            initClient();
        }

        if (db == null) {
            try {
                db = cloudant.database(databaseName, true);
            } catch (Exception e) {
                throw new RuntimeException("DB Not found", e);
            }
        }
        return db;
    }

    private PersistentHelper() {
    }
}
