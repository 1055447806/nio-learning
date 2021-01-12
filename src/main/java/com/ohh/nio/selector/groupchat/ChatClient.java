package com.ohh.nio.selector.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * NIO Client 多人聊天系统客户端
 *
 * @author Gary
 */
public class ChatClient {

    public void run() {
        try {
            Selector selector = Selector.open();
            SocketChannel socketChannel = (SocketChannel)SocketChannel.open(new InetSocketAddress(7000)).configureBlocking(false).register(selector, SelectionKey.OP_READ).channel();
            new Thread(()->{
                try {
                    while (socketChannel.isOpen()) {
                        selector.select();
                        Set<SelectionKey> selectedKeys = selector.selectedKeys();
                        for (Iterator<SelectionKey> iterator = selectedKeys.iterator(); iterator.hasNext(); iterator.remove()) {
                            SelectionKey key = iterator.next();
                            if (key.isReadable()) {
                                SocketChannel channel = (SocketChannel) key.channel();
                                ByteBuffer buffer = ByteBuffer.allocate(1024);
                                int len = channel.read(buffer);
                                String msg = new String(buffer.array(), 0, len);
                                SocketAddress remoteAddress = channel.getRemoteAddress();
                                System.out.println(String.format("From [%s]:%n%s", remoteAddress, msg));
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
            while (socketChannel.isOpen()) {
                Scanner scanner = new Scanner(System.in);
                String sendMsg = scanner.nextLine();
                socketChannel.write(ByteBuffer.wrap(sendMsg.getBytes()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ChatClient().run();
    }
}
