package com.ohh.nio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * 接收一台客户端的连接请求，并读取信息
 *
 * @author Gary
 */
public class ScatteringAndGathering {

    private static final ByteBuffer[] BUFFERS = new ByteBuffer[]{ByteBuffer.allocate(5), ByteBuffer.allocate(3)};

    public static void main(String[] args) throws IOException {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {

            // bind the endpoint to socket
            serverSocketChannel.socket().bind(new InetSocketAddress(7000));

            // accept connection request
            SocketChannel socketChannel = serverSocketChannel.accept();

            // read from the connection
            while (serverSocketChannel.isOpen()) {
                socketChannel.read(BUFFERS);
                Arrays.asList(BUFFERS).forEach(Buffer::flip);
                Arrays.asList(BUFFERS).forEach(buffer -> System.out.println(new String(buffer.array())));
                Arrays.asList(BUFFERS).forEach(Buffer::clear);
            }
        }
    }
}
