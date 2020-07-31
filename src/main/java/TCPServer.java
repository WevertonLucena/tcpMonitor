import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.text.NumberFormat;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class TCPServer {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        ServerSocket socket = new ServerSocket(2323);
        System.out.println("Aguardando requisições...");
        Socket clientSocket = socket.accept();
        while (true) {

            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
            DataInputStream in = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            byte[] bytes = new byte[52];
            in.read(bytes);

            String tipo = new String(bytes);
            System.out.println(tipo);


            if (tipo.trim().equals("1")) {
                processar(out);
            } else {
                System.out.println("###--");
            }
        }
    }

    private static void processar(DataOutputStream out) throws IOException {
        out.write("2".getBytes());
        out.flush();
        System.out.println("requisição respondida ...");
    }

}
