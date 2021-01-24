package ru.kpfu.itis.afarvazov.client;

import ru.kpfu.itis.afarvazov.Commands;
import ru.kpfu.itis.afarvazov.Message;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class MsgWriterThread extends Thread {

    BufferedWriter out;
    Scanner sc;
    Socket socket;

    public MsgWriterThread(Socket socket) throws IOException {
        this.socket = socket;
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        sc = new Scanner(System.in);
    }

    @Override
    public void run() {
        System.out.println("Как вас зовут?");
        String msgInput = sc.nextLine();
        String username = msgInput;
        while (true) {
            msgInput = sc.nextLine();
            try {
                if (msgInput.equals(Commands.EXIT.toString())) {
                    Message message = new Message(msgInput);
                    message.send(out);
                    this.interrupt();
                    break;
                } else {
                    Message message = new Message(username, msgInput);
                    message.send(out);
                }
            } catch (IOException e) {
                System.out.println("Произошла ошибка при отправке");
            }
        }
    }

}
