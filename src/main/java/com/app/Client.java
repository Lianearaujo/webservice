import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {


    public static String connection_socket(String ip, int port, String operation) {
        try {
            Socket s = new Socket(ip, port);
    
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            dos.writeUTF(operation);

            byte[] line = new byte[100];
            dis.read(line);
            String str = new String(line);
            System.out.println(str.trim());

            dis.close();
            dos.close();
            s.close();

            return str.trim();
        }
        catch (Exception err) {
            System.err.println(err);
        }
    
}