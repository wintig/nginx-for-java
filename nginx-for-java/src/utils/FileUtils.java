package utils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Description
 * @Author wintig
 * @Create 2018-11-10 下午7:49
 */
public class FileUtils {

    public static ByteBuffer readFile(String path) throws IOException {

        RandomAccessFile raf = new RandomAccessFile(path, "r");
        FileChannel channel = raf.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate((int) channel.size());
        channel.read(buffer);
        buffer.flip();
        return buffer;
    }

}
