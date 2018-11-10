import http.WintigHttpServer;

import java.io.IOException;

/**
 * @Description
 * @Author wintig
 * @Create 2018-11-09 上午1:41
 */
public class MainStart {

    public static void main(String[] args) throws IOException {
        new WintigHttpServer().start();
    }

}
