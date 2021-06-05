package io.devclub.chia.awesome;

import io.devclub.chia.awesome.facade.ChiaAwesomeRestFacade;
import io.devclub.chia.awesome.rest.request.ChiaServer;
import io.devclub.chia.awesome.rest.request.Disk;
import io.devclub.chia.awesome.rest.request.Ipv4Address;
import io.devclub.chia.awesome.service.NetworkService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Log4j2
@Component
@Scope("singleton")
public class ChiaAwesomeClient {
    private final ChiaServer chiaServer;
    private final NetworkService networkService;
    private final ChiaAwesomeRestFacade chiaAwesomeRestFacade;
    private final Pattern diskPattern = Pattern.compile("(.*)\\s\\((.*)\\)");

    public ChiaAwesomeClient(NetworkService networkService,
                             ChiaAwesomeRestFacade chiaAwesomeRestFacade) {
        this.networkService = networkService;
        this.chiaAwesomeRestFacade = chiaAwesomeRestFacade;
        List<Ipv4Address> ipv4Addresses = loadIpv4Addresses();
        List<Disk> disks = loadDisks();
        this.chiaServer = new ChiaServer(
                UUID.randomUUID().toString(),
                ipv4Addresses,
                disks
        );
    }

    private List<Ipv4Address> loadIpv4Addresses() {
        log.info("Loading ipv4 addresses");
        List<Ipv4Address> ipv4Addresses = this.networkService.getLocalAddresses()
                .stream()
                .map(interfaceAddress -> Ipv4Address.builder()
                        .address(interfaceAddress.getAddress().getHostAddress())
                        .mask(interfaceAddress.getNetworkPrefixLength())
                        .build())
                .collect(Collectors.toList());
        log.info("Loaded {} ipv4 addresses", ipv4Addresses.size());
        return ipv4Addresses;
    }

    @SneakyThrows
    private List<Disk> loadDisks() {
        log.info("Load disk information");
        List<Disk> disks = new ArrayList<>();
        long _byte = 1;
        long kB = _byte * 1024;
        long mB = kB * 1024;
        long gB = mB * 1024;

        for (FileStore store : FileSystems.getDefault().getFileStores()) {
            long total = store.getTotalSpace();
            long used = (store.getTotalSpace() - store.getUnallocatedSpace());
            long avail = store.getUsableSpace();
            if (total / gB > 500) {
                Matcher matcher = diskPattern.matcher(store.toString());
                if (matcher.find()) {
                    Disk disk = Disk.builder()
                            .path(matcher.group(1))
                            .name(store.name())
                            .totalSpace(total)
                            .usedSpace(used)
                            .availableSpace(avail)
                            .build();
                    disks.add(disk);
                }
            }

        }
        disks.forEach(disk -> log.info(disk.toString()));

        log.info("Disk information loaded");
        return disks;
    }

}
