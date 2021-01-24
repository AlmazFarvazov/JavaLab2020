package ru.kpfu.itis.afarvazov;

import java.io.BufferedWriter;
import java.io.IOException;

public class Message {

    private String author;
    private String content;

    public Message(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public Message(String content) {
        this.author = null;
        this.content = content;
    }

    @Override
    public String toString() {
        String msg = author != null ? author + ": " + content : content;
        return getFormattedMsg(msg);
    }

    public static String getFormattedMsg(String msg) {
        return msg + "\n";
    }

    public void send(BufferedWriter out) throws IOException {
        out.write(toString());
        out.flush();
    }

}
