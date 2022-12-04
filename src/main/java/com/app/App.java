package com.app;
import spark.ModelAndView;
import spark.Request;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        exception(Exception.class, (e, req, res) -> e.printStackTrace()); // print all exceptions
        staticFiles.location("/public");
        port(8088);

        get("/operations/:operation", (req, res) -> operations(req));

        get("/complex/:complex", (req, res) -> operations_complex(req));

        get("/hello", (req, res) -> "Hello World");
    }

    private static String operations(Request req) throws UnknownHostException, IOException {
        Map<String, Object> model = new HashMap<>();

        String operation = req.params(":operation");
       
        String result = connection_socket("127.0.0.1", 8082, operation);

        model.put("nome", result);

        return new VelocityTemplateEngine().render(new ModelAndView(model, "velocity/index.vm"));
    }

    private static String operations_complex(Request req) throws UnknownHostException, IOException {
        Map<String, Object> model = new HashMap<>();

        String operation = req.params(":complex");
       
        String result = connection_socket("127.0.0.1", 8083, operation);

        model.put("nome", result);

        return new VelocityTemplateEngine().render(new ModelAndView(model, "velocity/index.vm"));
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
        
            s.close();
        
            return str.trim();
        }
        catch (Exception err) {
            System.err.println(err);
        }
        return "0";
    }

}


    

   