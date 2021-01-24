package ru.kpfu.itis.afarvazov.client;

import com.beust.jcommander.JCommander;
import ru.kpfu.itis.afarvazov.Args;

import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] argv) {

        Args args = new Args();
        JCommander.newBuilder()
                .addObject(args)
                .build()
                .parse(argv);

        try {
            Socket socket = new Socket(args.serverIp, args.serverPort);
            MsgWriterThread msgWriterThread = new MsgWriterThread(socket);
            MsgReaderThread msgReaderThread = new MsgReaderThread(socket);
            msgReaderThread.start();
            msgWriterThread.start();
        } catch (IOException e) {
            System.out.println("Прозошла ошибка");
        }
    }

}
