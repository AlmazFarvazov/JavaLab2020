package ru.kpfu.itis.afarvazov.server;

import ru.kpfu.itis.afarvazov.Commands;
import ru.kpfu.itis.afarvazov.Message;

import java.io.*;
import java.net.Socket;

class SocketConnection extends Thread {

    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public SocketConnection(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start();
    }

    public void run() {
        while (true) {
            try {
                String clientMsg = in.readLine();
                if (clientMsg.equals(Commands.EXIT.toString())) {
                    close();
                    break;
                }
                sendAll(clientMsg);
            } catch (IOException e) {
                System.out.println("Ошибка");
                System.exit(0);
            }
        }
    }

    private void close() throws IOException {
        Message msg = new Message(Commands.EXIT.toString());
        msg.send(out);
        socket.close();
        in.close();
        out.close();
        Server.deleteSocketConnection(this);
        this.interrupt();
    }

    private void sendAll(String clientMsg) throws IOException {
        for (SocketConnection socketConnection : Server.getSocketConnections()) {
            Message msg = new Message(clientMsg);
            msg.send(socketConnection.out);
        }
    }

}