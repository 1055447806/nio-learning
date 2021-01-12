package com.ohh.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * 一个简单 BIO（Blocking IO） 模型
 * 可以使用 Telnet 工具进行客户端连接测试
 *
 * @author Gary
 */
public class BIOServer {

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        LOGGER.info("线程池已启动");

        ServerSocket serverSocket = new ServerSocket(8000);
        LOGGER.info("监听8000端口");

        while (!serverSocket.isClosed()) {
            final Socket socket = serverSocket.accept();
            LOGGER.info("客户端已连接");
            executorService.execute(() -> {
                try (InputStream in = socket.getInputStream()) {
                    byte[] bytes = new byte[1024];
                    int len;
                    while ((len = in.read(bytes)) != -1) {
                        System.out.println(new String(bytes, 0, len));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
