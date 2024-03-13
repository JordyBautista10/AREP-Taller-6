package org.escuelaing;

import static java.lang.Math.*;
import static spark.Spark.port;
import static spark.Spark.get;
import static spark.Spark.*;

public class SparkWebServer {

    public static void main(String[] args){
        port(getPort());

        staticFileLocation( "public");

        get("cliente", (req,res) -> {
            res.type("text/html");
            return page();
        });

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

        after((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Request-Method", "GET, POST, PUT, DELETE, OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type,Authorization");
        });

    }

    public static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
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

    public static String page(){
        return """
                <html>
                    <head>
                        <title>Form Example</title>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    </head>
                    <body>
                        <h1>Sin</h1>
                        <form action="/hello">
                            <label for="name">Value:</label><br>
                            <input type="number" id="x" name="name2" value="1.570796325"><br><br>
                            <input type="button" value="Submit" onclick="loadGetMsgSin()">
                        </form>\s
                        <div id="getrespmsg"></div>

                        <script>
                            function loadGetMsgSin() {
                                let nameVar = document.getElementById("x").value;
                                const xhttp = new XMLHttpRequest();
                                xhttp.onload = function() {
                                    document.getElementById("getrespmsg").innerHTML =
                                    this.responseText;
                                }
                                xhttp.open("GET", "/sin?id="+nameVar);
                                xhttp.send();
                            }
                        </script>

                        <h1>Cos</h1>
                        <form action="/hello">
                            <label for="name">Value:</label><br>
                            <input type="text" id="x2" name="name2" value="1.570796325"><br><br>
                            <input type="button" value="Submit" onclick="loadGetMsgCos()">
                        </form>\s
                        <div id="getrespmsg1"></div>

                        <script>
                            function loadGetMsgCos() {
                                let nameVar = document.getElementById("x2").value;
                                const xhttp = new XMLHttpRequest();
                                xhttp.onload = function() {
                                    document.getElementById("getrespmsg1").innerHTML =
                                    this.responseText;
                                }
                                xhttp.open("GET", "/cos?id="+nameVar);
                                xhttp.send();
                            }
                        </script>

                        <h1>Determinar si la cadena es palíndromo</h1>
                        <form action="/hello">
                            <label for="name">Palabra:</label><br>
                            <input type="text" id="word" name="name2" value="kayak"><br><br>
                            <input type="button" value="Submit" onclick="loadGetMsgPali()">
                        </form>\s
                        <div id="getrespmsg2"></div>

                        <script>
                            function loadGetMsgPali() {
                                let nameVar = document.getElementById("word").value;
                                const xhttp = new XMLHttpRequest();
                                xhttp.onload = function() {
                                    document.getElementById("getrespmsg2").innerHTML =
                                    this.responseText;
                                }
                                xhttp.open("GET", "/palindromo?word="+nameVar);
                                xhttp.send();
                            }
                        </script>

                        <h1>Magnitud de vector 2D</h1>
                        <form action="/hello">
                            <label for="name">X:</label><br>
                            <input type="number" id="x3" name="name3" value="kayak"><br><br>
                            <label for="name">Y:</label><br>
                            <input type="number" id="y3" name="name4" value="kayak"><br><br>
                            <input type="button" value="Submit" onclick="loadGetMsgMagn()">
                        </form>\s
                        <div id="getrespmsg3"></div>

                        <script>
                            function loadGetMsgMagn() {
                                let nameVar1 = document.getElementById("x3").value;
                                let nameVar2 = document.getElementById("y3").value;
                                const xhttp = new XMLHttpRequest();
                                xhttp.onload = function() {
                                    document.getElementById("getrespmsg3").innerHTML =
                                    this.responseText;
                                }
                                xhttp.open("GET", "/magnitud?x="+nameVar1+"&y="+nameVar2);
                                xhttp.send();
                            }
                        </script>
                    </body>
                </html>""";
    }

}
