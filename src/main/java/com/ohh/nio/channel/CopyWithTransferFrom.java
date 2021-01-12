package com.ohh.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * 使用 transferFrom 方法进行文件复制
 *
 * @author Gary
 */
public class CopyWithTransferFrom {

    private static final String SRC_PATH = "hello.txt";
    private static final String TARGET_PATH = "copy.txt";

    public static void main(String[] args) {
        try (FileChannel readChannel = new FileInputStream(SRC_PATH).getChannel();
             FileChannel writeChannel = new FileOutputStream(TARGET_PATH).getChannel()) {
            // copy by method transferFrom
            writeChannel.transferFrom(readChannel, 0, readChannel.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
