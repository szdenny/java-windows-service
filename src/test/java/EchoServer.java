import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.net.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        String ip = System.getProperty("server.ip"); // -Dserver.ip=127.0.0.1
        String port = System.getProperty("server.port"); // -Dserver.port=8081
        while (true) {
            try {
                beat(ip, port);
                Thread.sleep(6_000);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void beat(String ip, String port) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {

            long ts = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();

            String uri = String.format("http://%s:%s/beat?ts=%d", ip, port, ts);
            HttpPost httppost = new HttpPost(uri);
            System.out.println("Executing request: " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            response.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            httpclient.close();
        }
    }
}