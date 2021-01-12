package com.ohh.nio.selector.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

/**
 * NIO Server 多人聊天系统服务器
 *
 * @author Gary
 */
public class ChatServer {

    private final int port;
    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;

    private final Logger logger = Logger.getAnonymousLogger();

    public ChatServer(int port) throws IOException {
        this.serverSocketChannel = (ServerSocketChannel)
                ServerSocketChannel
                        .open()
                        .bind(new InetSocketAddress(this.port = port))
                        .configureBlocking(false)
                        .register(this.selector = Selector.open(), SelectionKey.OP_ACCEPT)
                        .channel();
    }

    public void run() {
        while (serverSocketChannel.isOpen()) {
            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (Iterator<SelectionKey> iterator = selectionKeys.iterator(); iterator.hasNext(); iterator.remove()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        SocketChannel socket = (SocketChannel)
                                serverSocketChannel
                                        .accept()
                                        .configureBlocking(false)
                                        .register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024))
                                        .channel();
                        logger.info(String.format("[%s] is online.", socket.getRemoteAddress()));
                    } else if (key.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        try {
                            String msg = new String(buffer.array(), 0, socketChannel.read(buffer));
                            buffer.clear();
                            logger.info(String.format("message from [%s]:%n%s", socketChannel.getRemoteAddress(), msg));
                            Set<SelectionKey> keys = selector.keys();
                            keys
                                    .stream()
                                    .map(SelectionKey::channel)
                                    .filter(channel -> channel instanceof SocketChannel && channel != socketChannel)
                                    .forEach(channel -> {
                                        try {
                                            ((SocketChannel) channel).write(ByteBuffer.wrap(msg.getBytes()));
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    });
                        } catch (IOException e) {
                            key.cancel();
                            socketChannel.close();
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("exception");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new ChatServer(7000).run();
    }
}
