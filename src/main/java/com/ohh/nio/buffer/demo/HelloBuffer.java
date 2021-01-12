package com.ohh.nio.buffer.demo;

import java.nio.IntBuffer;
import java.util.logging.Logger;

/**
 * 认识 Buffer
 *
 * @author Gary
 */
public class HelloBuffer {

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(5);

        // 写入
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put(i);
        }

        // 准备进行读取数据
        buffer.flip();

        // 读取
        while (buffer.hasRemaining()) {
            LOGGER.info("buffer.get() = " + buffer.get());
        }
    }
}
