package com.ohh.nio.channel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;

/**
 * 使用 FileChannel 对文件进行写入
 *
 * <pre>
 *     public FileChannel getChannel() {
 *         synchronized (this) {
 *             if (channel == null) {
 *                 channel = FileChannelImpl.open(fd, path, false, true, append, this);
 *             }
 *             return channel;
 *         }
 *     }
 * </pre>
 *
 * @author Gary
 */
public class WriteWithFileChannel {

    private static final String PATH = "hello.txt";
    private static final String TEXT = "Hello File Channel";

    public static void main(String[] args) throws IOException {

        // load buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(TEXT.getBytes());
        buffer.flip();

        // write file with file channel
        try (FileChannel channel = new FileOutputStream(PATH).getChannel()) {
            channel.write(buffer);
        }
    }
}
