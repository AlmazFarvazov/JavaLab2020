package ru.kpfu.itis.afarvazov.threads.multiload.app;

import com.beust.jcommander.JCommander;
import ru.kpfu.itis.afarvazov.threads.multiload.utils.Loader;

public class Main {
    private static final String ONE_THREAD_MODE = "one-thread";
    private static final String MULTI_THREAD_MODE = "multi-thread";

    public static void main(String[] argv) {
        Args args = new Args();

        JCommander.newBuilder()
                .addObject(args)
                .build()
                .parse(argv);
        int threads_count;
        if (args.mode.equals(ONE_THREAD_MODE)) threads_count = 1;
        else if (args.mode.equals(MULTI_THREAD_MODE)) threads_count = args.count;
        else throw new IllegalArgumentException("Unknown mode value");
        Loader loader = new Loader(threads_count, args.folder, args.files);
        loader.download();
    }
}
