package com.ohh.nio.buffer.exceptionExample;

import org.junit.Test;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public class UnderflowExceptionTest {

    /**
     * 放入 4 个 byte，值为 1，
     * 0000 0001 0000 0001 0000 0001 0000 0001
     * 每次读一个 short，
     * 0000 0001 0000 0001，
     * 值为 2 ^ 8 + 1 = 257，
     * 读完之后还继续读，就会抛出 BufferUnderflowException
     */
    @Test(expected = BufferUnderflowException.class)
    public void differentType() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put((byte) 1);
        buffer.put((byte) 1);
        buffer.put((byte) 1);
        buffer.put((byte) 1);
        buffer.flip();
        System.out.println("buffer.getShort() = " + buffer.getShort());
        System.out.println("buffer.getShort() = " + buffer.getShort());
        System.out.println("buffer.getShort() = " + buffer.getShort());
        System.out.println("buffer.getShort() = " + buffer.getShort());
    }
}
