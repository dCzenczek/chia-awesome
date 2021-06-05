package io.devclub.chia.awesome.socket.plot.receiver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

public class PlotReceiver {

//    public static void main(String[] args) throws IOException {
//        PlotReceiver server = new PlotReceiver();
//        SocketChannel socketChannel = server.createServerSocketChannel();
//        server.readFileFromSocketChannel(socketChannel);
//
//    }

    private void readFileFromSocketChannel(SocketChannel socketChannel) throws IOException {
        //Try to create a new file
        Path path = Paths.get("E:\\receiver\\YI041701.mp4");
        FileChannel fileChannel = FileChannel.open(path,
                EnumSet.of(StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING,
                        StandardOpenOption.WRITE)
        );
        //Allocate a ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (socketChannel.read(buffer) > 0) {
            buffer.flip();
            fileChannel.write(buffer);
            buffer.clear();
        }
        fileChannel.close();
        System.out.println("Receving file successfully!");
        socketChannel.close();
    }

    private SocketChannel createServerSocketChannel() throws IOException {
        ServerSocketChannel serverSocket = null;
        SocketChannel client = null;
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(9000));
        client = serverSocket.accept();

        System.out.println("connection established .." + client.getRemoteAddress());
        return client;
    }
}
