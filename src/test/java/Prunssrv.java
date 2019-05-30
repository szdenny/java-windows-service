import java.net.*;
import java.io.*;

public class Prunssrv {
    public static void prunsrvStartServer(String[] args) throws Exception {
        String[] newArgs = new String[1];
        newArgs[0] = System.getProperty("prunsrv.port"); // -Dprunsrv.port=8080
        EchoServer.main(newArgs);
    }

    public static void prunsrvStopServer(String[] args) throws Exception {
        String[] newArgs = new String[2];
        newArgs[0] = System.getProperty("prunsrv.server"); // -Dprunsrv.server=localhost
        newArgs[1] = System.getProperty("prunsrv.port"); // -Dprunsrv.port=8080
        newArgs[1] = "shutdown";
        EchoClient.main(newArgs);
    }
}