
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ConsoleServer {
    private Vector<ClientHandler> users;

    public Vector<ClientHandler> getUsers() {
        return users;
    }

    public ConsoleServer() {
        users = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;

        try {
            AuthService.connect();
            server = new ServerSocket(6001);
            System.out.println("Server started");

            while (true) {
                socket = server.accept();
                System.out.printf("Client [%s] connected\n", socket.getInetAddress());
                new ClientHandler(this, socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            AuthService.disconnect();
        }
    }

    public void subscribe(ClientHandler client) {
        users.add(client);
    }

    public void unsubscribe(ClientHandler client) {
        users.remove(client);
    }

    public synchronized boolean isNickBusy(String nick) {
        for (ClientHandler o : users) {
            if (o.getNickname().equals(nick)) {
                return true;
            }
        }
        return false;
    }


    public void broadcastMessage(String str) {
        for (ClientHandler c : users) {
            c.sendMsg(str);
        }
    }

    public void sendPrivateMessage(String msg, String nickAccept, String nickSend) {
        for (ClientHandler c : users) {
            if (c.getNickname().equals(nickAccept))
                c.sendMsg(msg);
            if (c.getNickname().equals(nickSend))
                c.sendMsg(msg);
        }
    }
}

