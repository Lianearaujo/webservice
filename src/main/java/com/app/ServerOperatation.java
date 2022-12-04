import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerOperatation {

    public static float somar(float x, float y){
        return x+y;
    }
    public static float diminuir(float x, float y){
        return x-y;
    }
    public static float multiplicar(float x, float y){
        return x*y;
    }
    public static float dividir(float x, float y){
        return x/y;
    }

    public static void main(String[] args){
        try {
            ServerSocket s = new ServerSocket(8082);
            String str;
            float result=0;
            while (true) {
                Socket c = s.accept();
                InputStream i = c.getInputStream();
                OutputStream o = c.getOutputStream();
                do {
                    byte[] line = new byte[100];
                    i.read(line);
                    str = new String(line);
                   
                    String str_opera = str.trim();
                    System.out.print(str_opera.length());
                    if (str_opera.length()>3){
                        String result_error = "Erro na sintaxe, por favor tente novamente";
                        o.write(String.valueOf(result_error).getBytes());
                    }
                    String variavel1 =  String.valueOf(str_opera.charAt(0));
                    String variavel2 =  String.valueOf(str_opera.charAt(2));
                    char operacao = str_opera.charAt(1);
                    if (operacao== '+'){
                        result = somar(Float.parseFloat(variavel1), Float.parseFloat(variavel2));
                    }else if (operacao== '-'){
                        result = diminuir(Float.parseFloat(variavel1), Float.parseFloat(variavel2));
                    }else if (operacao=='/'){
                        result= dividir(Float.parseFloat(variavel1), Float.parseFloat(variavel2));
                    }else if (operacao=='*'){
                        result = multiplicar(Float.parseFloat(variavel1), Float.parseFloat(variavel2));
                    }else {
                        String result_error = "Erro no operador";
                        o.write(String.valueOf(result_error).getBytes());
                    }
                    o.write(String.valueOf(result).getBytes());
                    str = new String(line);
                } while ( !str.trim().equals("bye") );
                c.close();
            }
        }
        catch (Exception err){
            System.err.println(err);
        }
    }

}