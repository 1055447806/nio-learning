package com.ohh.nio.selector.simplecs;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * NIO Client 客户端
 *
 * @author Gary
 */
public class NIOClient {

    // message
    private static final String MSG = "hello nio";

    public static void main(String[] args) throws IOException, InterruptedException {

        // initialize socketChannel
        final SocketChannel socketChannel = (SocketChannel)
                SocketChannel
                        .open()
                        .configureBlocking(false);

        // connect to remote server
        if (!socketChannel.connect(new InetSocketAddress(7000))) {
            while (!socketChannel.finishConnect()) {
                System.out.println("not connected yet , do something else.");
            }
        }

        // write the message
        socketChannel.write(ByteBuffer.wrap(MSG.getBytes()));
        socketChannel.write(ByteBuffer.wrap(MSG.getBytes()));
        Thread.currentThread().join();
    }
}
