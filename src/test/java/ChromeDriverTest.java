import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ChromeDriverTest {


    public static void main(String[] args) throws InterruptedException, IOException {

        String url = "https://mp3p.ysxs.site/audio/1/d/9/1d9598b148e4d0aa71c421c8a9b166f0.m4a?key=dd03d_b4af4_873a7_096f6_1d9513" ;

        String name = "第0001章崇祯元年1.mp3";

        Runtime.getRuntime().exec("curl -o /zhen/第0001章崇祯元年1.mp3 " + url);

        ProcessBuilder pb = new ProcessBuilder("curl", "--proxy","127.0.0.1:7890","-o", "/Users/susu/Downloads/mp3/第0001章崇祯元年1.mp3 ", url);
//        ProcessBuilder pb = new ProcessBuilder("curl", "--proxy","127.0.0.1:7890","-o", "~/zhen/第0001章崇祯元年1.mp3 ", url);

        System.out.println(pb.command().toString());
        ;
        try {
            Process p = pb.start();
            int exitCode = p.waitFor();

            System.out.println(exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

//        http(url);

    }


    private static void http(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // 输出下载结果
        System.out.println(response.toString());

    }

}
