package http;

import request.Request;
import response.Response;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @Description
 * @Author wintig
 * @Create 2018-11-09 上午1:42
 */
public class WintigHttpServer {

    private static final Integer port = 8080;

    public void start() throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress("localhost", port));
        serverSocketChannel.configureBlocking(false);

        System.out.println(String.format("服务器已启动，正在监听 %d 端口....", port));

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {

            // 每隔1秒检查一次
            if (selector.select(1000) == 0) {
                continue;
            }

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {

                SelectionKey selectionKey = iterator.next();


                if (selectionKey.isAcceptable()) {

                    // 这时候过来一个连接，可以连了
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);

                } else if (selectionKey.isReadable()) {

                    System.out.println("来个一个请求！");

                    // 处理request请求
                    Request.request(selectionKey);
                    selectionKey.interestOps(SelectionKey.OP_WRITE);

                } else if (selectionKey.isWritable()) {

                    System.out.println("返回一个请求！");

                    // 处理response请求
                    Response.response(selectionKey);
                }

                iterator.remove();
            }

        }

    }





}
