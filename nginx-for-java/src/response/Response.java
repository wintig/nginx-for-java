package response;

import http.Headers;
import utils.FileUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Optional;

/**
 * @Description
 * @Author wintig
 * @Create 2018-11-10 下午7:50
 */
public class Response {

    public static void response(SelectionKey selectionKey) throws IOException {

        SocketChannel channel = (SocketChannel) selectionKey.channel();

        // 获取request对象
        Optional<Headers> op = (Optional<Headers>) selectionKey.attachment();

        ByteBuffer bodyBuffer = null;

        try {

            bodyBuffer = FileUtils.readFile(op.get().getPath());
            ResponseHeaders headers = new ResponseHeaders(200);
            headers.setContentLength(bodyBuffer.capacity());
            headers.setContentType("Content-Type:image/jpeg");
            ByteBuffer headerBuffer = ByteBuffer.wrap(headers.toString().getBytes("UTF-8"));
            channel.write(new ByteBuffer[]{headerBuffer, bodyBuffer});

        } catch (Exception e) {

            System.out.println(op.get().getPath() + " 资源未找到！");

            // 没找到资源
            ResponseHeaders headers = new ResponseHeaders(404);
            headers.setContentType("Content-Type:text/html;Charset=" + "UTF-8" + "\r\n");
            ByteBuffer headerBuffer = ByteBuffer.wrap(headers.toString().getBytes("UTF-8"));
            channel.write(new ByteBuffer[]{headerBuffer});

        } finally {
            channel.close();
        }

    }

}
