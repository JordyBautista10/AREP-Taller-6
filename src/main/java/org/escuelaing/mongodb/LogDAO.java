package org.escuelaing.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Sorts.descending;

public class LogDAO {

    private final MongoCollection<Document> logsFile;

    public LogDAO(MongoDatabase base) {
        logsFile = base.getCollection(MongoUtil.BASE_NAME);
    }

    public void addLog (String logName) {
        try {
            Document doc = new Document("Log", logName).append("Hour", LocalDateTime.now().toString());
            logsFile.insertOne(doc);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public List<Document> getLogs() {
        FindIterable<Document> allLogs = logsFile.find().sort(descending("Hour")).limit(10);
        ArrayList<Document> logsList = new ArrayList<>();
        allLogs.forEach(logsList::add);
        return logsList;
    }

    public void deleteLogs() {
        FindIterable<Document> allLogs = logsFile.find();
        allLogs.forEach(logsFile::deleteOne);
    }
}
