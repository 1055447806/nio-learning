package com.ohh.nio.channel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用 FileChannel 对文件进行写入
 *
 * @author Gary
 */
public class ReadWithFileChannel {

    private static final String PATH = "hello.txt";
    private static final File FILE = new File(PATH);

    public static void main(String[] args) {

        // initialize buffer
        ByteBuffer buffer = ByteBuffer.allocate((int) FILE.length());

        // read to buffer
        try (FileChannel channel = new FileInputStream(FILE).getChannel()) {
            channel.read(buffer);
            buffer.flip();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // show data
        System.out.println(new String(buffer.array()));
    }
}
