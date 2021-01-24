package ru.kpfu.itis.afarvazov.server;

import com.beust.jcommander.JCommander;
import ru.kpfu.itis.afarvazov.Args;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private static ArrayList<SocketConnection> socketConnections = new ArrayList<>();

    public static void main(String[] argv) {

        Args args = new Args();
        JCommander.newBuilder()
                .addObject(args)
                .build()
                .parse(argv);

        ServerSocket server = null;

        try {
            server = new ServerSocket(args.port);
        } catch (IOException e) {
            System.out.println("Произошла ошибка с сервером");
        }

        if (server != null) {
            while (true) {
                try {
                    Socket socket = server.accept();
                    socketConnections.add(new SocketConnection(socket));
                } catch (IOException e) {
                    break;
                }
            }
        }
    }

    public static ArrayList<SocketConnection> getSocketConnections() {
        return socketConnections;
    }

    public static void deleteSocketConnection(SocketConnection socketConnection) {
        socketConnections.remove(socketConnection);
    }

}
