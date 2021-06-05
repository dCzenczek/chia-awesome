package io.devclub.chia.awesome.service;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.net.Inet4Address;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Log4j2
@Service
@Scope("singleton")
public class NetworkService {

    private List<InterfaceAddress> localAddresses = new ArrayList<>();

    @SneakyThrows
    public NetworkService() {
        loadLocalAddresses();
    }

    public List<InterfaceAddress> getLocalAddresses() {
        return localAddresses;
    }

    private void loadLocalAddresses() throws SocketException {
        long startTime = System.currentTimeMillis();
        log.info("Loading local addresses");
        localAddresses = new ArrayList<>();
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();

            for (InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
                if ((address.getAddress() instanceof Inet4Address) && address.getAddress().isSiteLocalAddress()) {
                    localAddresses.add(address);
                }
            }
        }
        localAddresses.forEach(interfaceAddress -> {
            log.info("Interface " + interfaceAddress.getAddress().getHostAddress() + "/" + interfaceAddress.getNetworkPrefixLength() + " loaded");
        });
        long endTime = System.currentTimeMillis();
        log.info("Local addresses loaded in " + (endTime - startTime) + "ms");
    }

}
