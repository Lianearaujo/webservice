package com.app;
import spark.ModelAndView;
import spark.Request;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        exception(Exception.class, (e, req, res) -> e.printStackTrace()); // print all exceptions
        staticFiles.location("/public");
        port(8088);

        get("/operations", (req, res) -> operations(req));

        get("/complex", (req, res) -> operations_complex(req));

        get("/hello", (req, res) -> "Hello World");
    }

    private static String operations(Request req) throws UnknownHostException, IOException {
        Map<String, Object> model = new HashMap<>();

        String data = req.body();
       
        String result = connection_socket("127.0.0.1", 8082, data);

        model.put("nome", result);

        return result;
    }

    private static String operations_complex(Request req) throws UnknownHostException, IOException {
        Map<String, Object> model = new HashMap<>();
    

        String data = req.body();
       
        String result = connection_socket("127.0.0.1", 8083, data);

        model.put("nome", result);

        return result;
    }

    private static String connection_socket(String ip, int port, String operation) {
        try{ 
            Socket s = new Socket(ip, port);

            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        
            dos.writeUTF(operation);
        
            byte[] line = new byte[100];
            dis.read(line);
            String str = new String(line);
            System.out.println(str.trim());
        
            return str.trim();
        }
        catch (Exception err) {
            System.err.println(err);
        }
        return "0";
    }

}


    

   