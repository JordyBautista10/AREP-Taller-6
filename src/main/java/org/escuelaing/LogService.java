package org.escuelaing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoDatabase;
import org.escuelaing.mongodb.LogDAO;
import org.escuelaing.mongodb.MongoUtil;

import static java.lang.Math.*;
import static spark.Spark.port;
import static spark.Spark.get;

public class LogService {

    private static LogDAO logsDAO;

    public static void main(String[] args){
        port(getPort());

        inicializarMongo();

        get("sin", (req,res) -> {
            float x = Float.parseFloat(req.queryParams("id"));
            res.type("text/html");
            return "Sin(" + x + ") = " + sin(x);
        });

        get("cos", (req,res) -> {
            float x = Float.parseFloat(req.queryParams("id"));
            res.type("text/html");
            return "Cos(" + x + ") = " + cos(x);
        });

        get("palindromo", (req,res) -> {
            String palabra = req.queryParams("word");
            res.type("text/html");
            return palabra + " es palindromo: " + palindromo(palabra);
        });

        get("magnitud", (req,res) -> {
            Double x = Double.valueOf(req.queryParams("x"));
            Double y = Double.valueOf(req.queryParams("y"));
            res.type("text/html");
            return " La magnitud de (" + x + ", " + y +") es: " + sqrt( (x*x)  +(y*y));
        });

        get("logs", (req,res) -> {
            String x = req.queryParams("msg");
            res.type("application/json");
            logsDAO.addLog(x);
            return /*"yes" ;*/ new ObjectMapper().writeValueAsString(logsDAO.getLogs());
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

    public static boolean palindromo(String word) {
        int left = 0;
        int right = word.length() - 1;

        word = word.toLowerCase();
        while (left < right) {
            if (word.charAt(left) != word.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }

}
