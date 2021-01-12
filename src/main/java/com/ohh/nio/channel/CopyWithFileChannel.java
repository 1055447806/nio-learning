package com.ohh.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用 FileChannel 实现文件的拷贝
 *
 * @author Gary
 */
public class CopyWithFileChannel {

    private static final String SRC_PATH = "hello.txt";
    private static final String TARGET_PATH = "copy.txt";

    private static final int BUFFER_CAPACITY = 5;

    public static void main(String[] args) {

        // initialize buffer
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_CAPACITY);

        // build read and write channel
        try (FileChannel readChannel = new FileInputStream(SRC_PATH).getChannel();
             FileChannel writeChannel = new FileOutputStream(TARGET_PATH).getChannel()) {
            // read into buffer and write into file
            while (readChannel.read(buffer) != -1) {
                buffer.flip();
                writeChannel.write(buffer);
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
