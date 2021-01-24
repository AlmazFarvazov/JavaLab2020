package ru.kpfu.itis.afarvazov.threads.multiload.utils;

import ru.kpfu.itis.afarvazov.threads.multiload.pool.ThreadPool;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class Loader {

    private List<URL> urls;
    private String folder;
    private ThreadPool pool;

    public Loader(int threads_count, String folder, List<URL> urls) {
        this.urls = urls;
        this.folder = folder;
        pool = new ThreadPool(threads_count);
    }

    public void download() {
        for (int i = 0; i < urls.size(); i++) {
            URL url = urls.get(i);
            int file_number = i;
            Runnable load_thread = () -> {
                try (InputStream in = url.openStream()) {
                    Files.copy(in, Paths.get(
                            folder + "/" + file_number + "." + url.openConnection().getContentType().split("/")[1]),
                            StandardCopyOption.REPLACE_EXISTING
                    );
                } catch (IOException e) {
                    throw new IllegalArgumentException(e);
                }
            };
            pool.submit(load_thread);
        }
    }

}
