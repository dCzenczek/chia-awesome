package io.devclub.chia.awesome.sender;


public class SendFile {
    private final String address;
    private final int port;
    private final String filepath;

    public SendFile(String address, int port, String filepath) {
        this.address = address;
        this.port = port;
        this.filepath = filepath;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public String getFilepath() {
        return filepath;
    }
}
