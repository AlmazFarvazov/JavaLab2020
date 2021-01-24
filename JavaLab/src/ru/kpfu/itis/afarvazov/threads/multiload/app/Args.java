package ru.kpfu.itis.afarvazov.threads.multiload.app;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.IntegerConverter;
import ru.kpfu.itis.afarvazov.threads.multiload.utils.StringListConverter;

import java.net.URL;
import java.util.ArrayList;

@Parameters(separators = "=")
public class Args {
    @Parameter(names = {"--mode"})
    public String mode;
    @Parameter(names = {"--count"}, converter = IntegerConverter.class)
    public int count;
    @Parameter(names = {"--files"}, converter = StringListConverter.class)
    public ArrayList<URL> files;
    @Parameter(names = {"--folder"})
    public String folder;
}
