package org.escuelaing.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class LogDAO {

    private final MongoCollection<Document> logsFile;

    public LogDAO(MongoDatabase base) {
        logsFile = base.getCollection(MongoUtil.BASE_NAME);
    }

    public boolean addLog (String logName) {
        try {
            Document doc = new Document("Log", logName).append("Hour", LocalDateTime.now());
            logsFile.insertOne(doc);
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public ArrayList<Document> getLogs() {
        FindIterable<Document> allLogs = logsFile.find();
        ArrayList<Document> logsList = new ArrayList<Document>();
        allLogs.forEach(logsList::add);
        return logsList;
    }
}
