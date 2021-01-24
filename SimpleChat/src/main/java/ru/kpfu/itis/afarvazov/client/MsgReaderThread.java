package ru.kpfu.itis.afarvazov.client;

import ru.kpfu.itis.afarvazov.Commands;

import java.io.*;
import java.net.Socket;

public class MsgReaderThread extends Thread {

    private BufferedReader in;
    private Socket socket;

    public MsgReaderThread(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        while (true) {
            try {
                String response = in.readLine();
                if (response.equals(Commands.EXIT.toString())) {
                    close();
                    break;
                }
                System.out.println(response);
            } catch (IOException e) {
                System.out.println("Произошла ошибка");
                close();
            }
        }
    }

    private void close() {
        try {
            in.close();
            socket.close();
            this.interrupt();
            System.exit(0);
        } catch (IOException e) {
            System.exit(0);
        }
    }

}
