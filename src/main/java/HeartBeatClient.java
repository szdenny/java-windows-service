import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HeartBeatClient {

    public static void main(String[] args) {
        final String ip = System.getProperty("server.ip"); // -Dserver.ip=127.0.0.1
        final String port = System.getProperty("server.port"); // -Dserver.port=8081
        final String rdoProcess = System.getProperty("rdo.process");// -Drdo.process=rdo.exe

        if (rdoProcess == null) {
            return;
        }
        while (true) {
            try {
                if (isProcessStarted(rdoProcess)) {
                    beat(ip, port);
                }
                Thread.sleep(6_000);
            } catch (Exception e) {
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
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpclient.close();
        }
    }

    public static boolean isProcessStarted(String processName) {
        Runtime runtime = Runtime.getRuntime();

        try {
            Process process = runtime.exec("cmd /c Tasklist");

            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String s;
            while ((s = in.readLine()) != null) {
                s = s.toLowerCase();
                System.out.println(s);
                if (s.startsWith(processName)) {
                    System.out.println("==========>" + processName);
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
