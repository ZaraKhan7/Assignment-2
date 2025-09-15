// TCPClient.java
import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 9091);

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        String userMsg;
        System.out.println("Connected to server. Type 'exit' to quit.");

        while (true) {
            System.out.print("You: ");
            userMsg = userInput.readLine();

            if (userMsg.equalsIgnoreCase("exit")) {
                break;
            }

            out.println(userMsg);
            String response = in.readLine();
            System.out.println("Server: " + response);
        }

        socket.close();
    }
}
