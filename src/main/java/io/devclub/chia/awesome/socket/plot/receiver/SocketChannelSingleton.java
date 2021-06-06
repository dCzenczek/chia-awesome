package io.devclub.chia.awesome.socket.plot.receiver;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

@Log4j2
public final class SocketChannelSingleton {

    private static SocketChannel socketChannel;

    public static SocketChannel getSocketChannel() throws IOException {
        if (socketChannel == null) {
            socketChannel = createServerSocketChannel();
        }
        return socketChannel;
    }

    private SocketChannel createServerSocketChannel() throws IOException {
        ServerSocketChannel serverSocket = null;
        SocketChannel client = null;
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(9000));
        client = serverSocket.accept();

        log.info("connection established .." + client.getRemoteAddress());
        return client;
    }
}
