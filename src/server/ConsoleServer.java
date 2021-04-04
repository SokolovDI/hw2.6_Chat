package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ConsoleServer {
    private final Vector<ClientHandler> users;

    public ConsoleServer() {
        users = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;

        try {
            server = new ServerSocket(6001);
            System.out.println("Server started");

            while (true) {
                socket = server.accept();
                System.out.printf("Client [%s] connected\n", socket.getInetAddress());

                subscribe(new ClientHandler(this, socket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void subscribe (ClientHandler client){
        users.add(client);
    }

    public void unsubscribe (ClientHandler client){
        users.remove(client);
    }

    public void broadcastMessage(String str){
        for (ClientHandler c : users){
            c.sendMsg(str);
        }
    }
}

