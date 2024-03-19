package org.escuelaing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

public class SparkWebService {

    private static final String[] datos = new String[]{"35001", "35002", "35003"};
    private static int currentIndex = 0;

    public static void main(String[] args) {

        staticFileLocation( "public");

        post("log", (req,res) -> {
            String message = req.queryParams("msg");
            res.type("application/json");
            return makeRequest(roundRobin(message));
        });
    }

    public static String roundRobin(String message) {
        currentIndex = (currentIndex + 1) % datos.length;
        System.out.println( "Servidor que antiende: "  + datos[currentIndex]);
        return "http://localhost:" + datos[currentIndex] + "/logs?msg=" + message;
    }

    public static String makeRequest(String url) throws IOException {

        URI uri = URI.create(url);
        URL obj = uri.toURL();
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        StringBuilder response = new StringBuilder();
        // Encabezado necesario en todas las peticiones
        response.append("""
                HTTP/1.1 200 OK\r
                Content-Type:application/json\r
                Access-Control-Allow-Origin: *\r
                \r
                """);
        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
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
}
