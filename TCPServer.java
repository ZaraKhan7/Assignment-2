import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9091);
        System.out.println("Server is running and ready for multiple clients...");

        while (true) {
            Socket clientSocket = serverSocket.accept(); // Accept new client
            System.out.println("New client connected: " + clientSocket.getInetAddress());

            // Handle this client in a separate thread
            ClientHandler handler = new ClientHandler(clientSocket);
            Thread t = new Thread(handler);
            t.start();

          
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

   
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Client (" + clientSocket.getInetAddress() + ") says: " + message);
                out.println("Server received: " + message);
            }
        } catch (IOException e) {
            System.out.println("Connection error with client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Client (" + clientSocket.getInetAddress() + ") disconnected.");
        }
    }
}
