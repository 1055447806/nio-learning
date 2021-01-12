package com.ohh.nio.buffer.demo;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * MappedByteBuffer 可以对文件直接在 "堆外内存" 进行修改
 *
 * @author Gary
 */
public class MappedByteBufferExample {

    private static final String PATH = "file.txt";

    public static void main(String[] args) {
        try (FileChannel channel = new RandomAccessFile(PATH,"rw").getChannel()){
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 1024);
            buffer.put(0, (byte) 'A');
            buffer.put(1, (byte) 'B');
            buffer.put(2, (byte) 'C');
            buffer.put(3, (byte) 'D');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
