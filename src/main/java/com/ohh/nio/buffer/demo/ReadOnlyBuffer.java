package com.ohh.nio.buffer.demo;

import java.nio.ByteBuffer;
import java.util.stream.IntStream;

/**
 * asReadOnlyBuffer 使 Buffer 变为只读 Buffer
 *
 * @author Gary
 */
public class ReadOnlyBuffer {
    public static void main(String[] args) {

        /*ByteBuffer buffer = ByteBuffer.allocate(1024);*/
        ByteBuffer buffer = ByteBuffer.allocate(1024).asReadOnlyBuffer();

        IntStream.iterate(0, i -> ++i).limit(64).forEach(i -> buffer.put((byte) i));
        buffer.flip();
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        while (readOnlyBuffer.hasRemaining()) {
            System.out.println("readOnlyBuffer.get() = " + readOnlyBuffer.get());
        }
    }
}
