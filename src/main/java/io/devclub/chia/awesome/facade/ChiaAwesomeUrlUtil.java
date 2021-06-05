package io.devclub.chia.awesome.facade;

public class ChiaAwesomeUrlUtil {

    private final static String REGISTER_SERVER_URL = "/network/register/server";

    public static String getRegisterServerUrl(String address){
        return address + REGISTER_SERVER_URL;
    }
}
