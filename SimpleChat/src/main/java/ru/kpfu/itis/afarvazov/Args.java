package ru.kpfu.itis.afarvazov;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class Args {

    @Parameter(names = {"--port"})
    public int port;

    @Parameter(names = "--server-ip")
    public String serverIp;

    @Parameter(names = "--server-port")
    public int serverPort;

}
