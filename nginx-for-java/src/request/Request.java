package request;


import http.Headers;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Optional;

/**
 * @Description
 * @Author wintig
 * @Create 2018-11-09 上午2:15
 */
public class Request {

    public static void request(SelectionKey selectionKey) throws IOException {

        SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 将通道中的数据写入缓冲区
        socketChannel.read(buffer);

        // 开启读模式
        buffer.flip();

        byte[] bytes = new byte[buffer.limit()];
        buffer.get(bytes);
        String headerStr = new String(bytes);

        // 解析http
        Headers headers = parseHeader(headerStr);

        // 将我们解析好的http请求交给Response处理
        selectionKey.attach(Optional.of(headers));

    }

    private static Headers parseHeader(String headerStr) {

        // 获取第一行
        int index = headerStr.indexOf("\r\n");
        Headers headers = new Headers();
        String firstLine = headerStr.substring(0, index);
        String[] parts = firstLine.split(" ");

        // GET /1.png HTTP/1.1
        headers.setMethod(parts[0]);
        headers.setPath(parts[1]);
        headers.setVersion(parts[2]);

        parts = headerStr.split("\r\n");
        for (String part : parts) {
            index = part.indexOf(":");
            if (index == -1) {
                continue;
            }
            String key = part.substring(0, index);
            if (index == -1 || index + 1 >= part.length()) {
                headers.set(key, "");
                continue;
            }
            String value = part.substring(index + 1);
            headers.set(key, value);
        }

        return headers;
    }


}
