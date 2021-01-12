package com.ohh.nio.selector.simplecs;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 * NIO Server 服务器
 *
 * @author Gary
 */
public class NIOServer {

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    public static void main(String[] args) throws IOException {

        // initialize, serverSocketChannel register to selector
        final Selector selector = Selector.open();
        final ServerSocketChannel serverSocketChannel = (ServerSocketChannel)
                ServerSocketChannel
                        .open()
                        .bind(new InetSocketAddress(7000))
                        .configureBlocking(false)
                        .register(selector, SelectionKey.OP_ACCEPT)
                        .channel();

        // fetch events from selector
        while (serverSocketChannel.isOpen()) {
            if (selector.select(3000) == 0) {
                LOGGER.info("服务器在 3 秒内无客户端连接");
            } else {
                for (Iterator<SelectionKey> iterator = selector.selectedKeys().iterator(); iterator.hasNext(); iterator.remove()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isAcceptable()) {
                        // accept and register
                        SocketChannel socketChannel = (SocketChannel)
                                serverSocketChannel
                                        .accept()
                                        .configureBlocking(false)
                                        .register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024))
                                        .channel();
                        LOGGER.info(String.format("客户端[{%s}]已连接", socketChannel.getRemoteAddress()));
                    } else if (selectionKey.isReadable()) {
                        // read
                        ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                        LOGGER.info(new String(buffer.array(), 0, ((SocketChannel) selectionKey.channel()).read(buffer)));
                    }
                }
            }
        }

        // FIXME: 2021/1/12 如果客户端关闭，会抛出 Exception in thread "main" java.io.IOException: 远程主机强迫关闭了一个现有的连接。
        // Exception in thread "main" java.io.IOException: 远程主机强迫关闭了一个现有的连接。
        //      at sun.nio.ch.SocketDispatcher.read0(Native Method)
        //      at sun.nio.ch.SocketDispatcher.read(SocketDispatcher.java:43)
        //      at sun.nio.ch.IOUtil.readIntoNativeBuffer(IOUtil.java:223)
        //      at sun.nio.ch.IOUtil.read(IOUtil.java:197)
        //      at sun.nio.ch.SocketChannelImpl.read(SocketChannelImpl.java:380)
        //      at com.ohh.nio.selector.simpleCS.NIOServer.main(NIOServer.java:51)
    }
}
