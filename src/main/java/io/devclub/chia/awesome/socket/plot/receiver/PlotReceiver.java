package io.devclub.chia.awesome.socket.plot.receiver;

import io.devclub.chia.awesome.rest.request.Plot;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

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

@Log4j2
public class PlotReceiver extends Thread {

    private Integer socketChannelPlot;
    private String pathToStorePlot;

    public PlotReceiver(Integer socketChannelPlot, String pathToStorePlot) {
        this.socketChannelPlot = socketChannelPlot;
        this.pathToStorePlot = pathToStorePlot;
    }

    @Override
    public void run() {
        super.run();
        try {
            readFileFromSocketChannel(createServerSocketChannel(socketChannelPlot),pathToStorePlot);
        } catch (IOException e) {
            //Throwing runtime and catching in main thread
            throw new RuntimeException();
        }

    }

    public boolean readFileFromSocketChannel(SocketChannel socketChannel, String pathString) throws IOException {
        //Try to create a new file
        Path path = Paths.get(pathString);
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
        return true;
    }

    private SocketChannel createServerSocketChannel(Integer socketChannelPlot) throws IOException {
        ServerSocketChannel serverSocket = null;
        SocketChannel client = null;
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(socketChannelPlot));
        client = serverSocket.accept();

        log.info("connection established .." + client.getRemoteAddress());
        return client;
    }


}
