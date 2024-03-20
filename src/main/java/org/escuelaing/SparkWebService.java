package org.escuelaing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import static spark.Spark.*;

public class SparkWebService {

    private static final String[] servers = new String[]{"logservice1:35000", "logservice2:35000", "logservice3:35000"};
    private static int currentIndex = 0;

    public static void main(String[] args) {
        port(getPort());

        staticFileLocation( "public");

        get("logs", (req,res) -> {
            String message = req.queryParams("msg");
            res.type("application/json");
            return makeRequest(roundRobin(message));
        });
    }

    public static String roundRobin(String message) {
        currentIndex = (currentIndex + 1) % servers.length;
        System.out.println( "Servidor que antiende: "  + servers[currentIndex]);

        //return "http://localhost:" + datos[currentIndex] + "/logs?msg=" + message;        //uso local
        return "http://" +  servers[currentIndex] + "/logs?msg=" + message;     // uso en nube
    }

    public static String makeRequest(String url) throws IOException {

        URL obj = URI.create(url).toURL();
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        StringBuilder response = new StringBuilder();

        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response);
        } else {
            System.out.println("GET request not worked");
        }
        System.out.println("GET DONE");

        return response.toString();
    }

    public static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 8080;
    }
}
