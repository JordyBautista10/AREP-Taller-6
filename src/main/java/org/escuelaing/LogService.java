package org.escuelaing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoDatabase;
import org.escuelaing.mongodb.LogDAO;
import org.escuelaing.mongodb.MongoUtil;

import static spark.Spark.port;
import static spark.Spark.get;

public class LogService {

    private static LogDAO logsDAO;

    public static void main(String[] args){
        port(getPort());

        inicializarMongo();

        get("logs", (req,res) -> {
            String x = req.queryParams("msg");
            res.type("application/json");
            logsDAO.addLog(x);
            return new ObjectMapper().writeValueAsString(logsDAO.getLogs());
        });

        get("logsdel", (req,res) -> {
            logsDAO.deleteLogs();
            return "Logs deleted successfully";
        });

    }

    public static void inicializarMongo(){
        MongoDatabase database = MongoUtil.getDatabase();
        logsDAO = new LogDAO(database);



        logsDAO.getLogs().forEach(System.out::println);
    }

    public static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35001;
    }



}
