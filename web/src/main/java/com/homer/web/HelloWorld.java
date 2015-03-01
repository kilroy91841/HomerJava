package com.homer.web;

import static spark.Spark.*;

/**
 * Created by arigolub on 2/28/15.
 */
public class HelloWorld {
    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello world");
    }
}
