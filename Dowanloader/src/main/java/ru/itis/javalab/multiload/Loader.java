package ru.itis.javalab.multiload;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Loader {

    private List<URL> urls;
    private String folder;
    private ExecutorService pool;

    public Loader(int threads_count, String folder, List<URL> urls) {
        this.urls = urls;
        this.folder = folder;
        pool = Executors.newFixedThreadPool(threads_count);
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
