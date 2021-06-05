package io.devclub.chia.awesome.socket.plot.sender;

import io.devclub.chia.awesome.sender.SendFile;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class PlotSender {

    private List<SendFile> sendFiles;

//    public static void main(String[] args) throws IOException {
//        PlotSender client = new PlotSender(Collections.singletonList(new SendFile("localhost", 9000,
//                "D:\\Filmy\\Squash\\Gosia Edi Ja\\YI041701.mp4")));
//        client.transfer();
//
//    }

    public PlotSender(List<SendFile> sendFiles) {
    }

    public void transfer() throws IOException {
        for (SendFile sendFile: sendFiles) {
            System.out.println("Start sending file: " + sendFile.getFilepath() + " to " + sendFile.getAddress() + ":" + sendFile.getPort());
            sendFile(sendFile);
            System.out.println("File: " + sendFile.getFilepath() + " send to " + sendFile.getAddress() + ":" + sendFile.getPort());
        }
    }

    private void sendFile(SendFile sendFile) throws IOException {
        SocketChannel channel = createChannel(sendFile);
        Path path = Paths.get(sendFile.getFilepath());
        FileChannel inChannel = FileChannel.open(path);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (inChannel.read(buffer) > 0) {
            buffer.flip();
            channel.write(buffer);
            buffer.clear();
        }
        channel.close();
    }

    private SocketChannel createChannel(SendFile sendFile) throws IOException {
        //Remember that is code only works on blocking mode
        SocketChannel socketChannel = SocketChannel.open();

        //we don't need call this function as default value of blocking mode = true
        socketChannel.configureBlocking(true);

        SocketAddress sockAddr = new InetSocketAddress(sendFile.getAddress(), sendFile.getPort());
        socketChannel.connect(sockAddr);
        return socketChannel;
    }
}
