package io.devclub.chia.awesome.cron;

import io.devclub.chia.awesome.ChiaAwesomeClient;
import io.devclub.chia.awesome.api.rest.ChiaAwesomeRestApi;
import io.devclub.chia.awesome.api.rest.model.ChiaServer;
import io.devclub.chia.awesome.api.rest.model.Disk;
import io.devclub.chia.awesome.api.rest.model.Ipv4Address;
import io.devclub.chia.awesome.config.ClientConfig;
import io.devclub.chia.awesome.config.ThresholdType;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Component
public class PlotMovementCronProcessor {

    private static final String DEFAULT_PORT = "8080";
    private final ChiaServer chiaServer;
    private final ClientConfig clientConfig;

    public PlotMovementCronProcessor(ChiaAwesomeClient chiaAwesomeClient) {
        this.chiaServer = chiaAwesomeClient.getChiaServer();
        this.clientConfig = chiaAwesomeClient.getClientConfig();
    }

    public void checkAndSendPlotIfNeeded() {
        if (isPlotMovementNeededBetweenServers()) {
            log.info("Plot movement needed, getting servers that need plots");
            List<Ipv4Address> serversIp = getServersIpWithoutPlots();
            log.info("Get server which need plot the most");
            try {
                Ipv4Address serverIpWhichNeedPlotTheMost = getServerIpWhichNeedPlotTheMost(serversIp);

            } catch (URISyntaxException e) {
                // TODO: 05/06/2021 handle this exception
                e.printStackTrace();
            }
        } else if (isPlotMovementNeededBetweenDisks()) {
            log.info("Plot movement needed between disks");
            List<Disk> overloadedDisks = new ArrayList<>(getOverLoadedDisks());
            List<Disk> unfilledDisks = getUnfilledDisks();
            unfilledDisks.forEach(toDisk -> {
                Disk fromDisk = overloadedDisks.get(0);
                movePlot(fromDisk, toDisk);
                overloadedDisks.remove(fromDisk);
            });
        }
    }

    private void movePlot(Disk fromDisk, Disk toDisk) {
        log.info("Move random plot from disk: {} to disk: {}", fromDisk.getPath(), toDisk.getPath());
    }

    private List<Disk> getUnfilledDisks() {
        log.info("Getting unfilled disks");
        return Collections.emptyList();
    }

    private List<Disk> getOverLoadedDisks() {
        log.info("Getting overloaded disks");

        return Collections.emptyList();
    }

    public boolean isPlotMovementNeededBetweenDisks() {
        log.info("Checking if plot movement is needed between disks");
        List<Boolean> collect = chiaServer.getDisks()
                .stream()
                .map(this::checkDiskThreshold)
                .distinct()
                .collect(Collectors.toList());

        return collect.size() == 2;
    }

    public boolean isPlotMovementNeededBetweenServers() {
        log.info("Checking if plot movement is needed between severs");
        boolean anyDiskNeed = chiaServer.getDisks()
                .stream()
                .anyMatch(disk -> {
                    if (ThresholdType.PERCENT.equals(clientConfig.getPlotMovementThreshold().getType())) {
                        if (disk.getUsedSpace() / disk.getTotalSpace() * 100 < clientConfig.getPlotMovementThreshold().getValue()) {
                            return true;
                        }
                    }
                    if (ThresholdType.GIB_SPACE.equals(clientConfig.getPlotMovementThreshold().getType())) {
                        if (disk.getAvailableSpace() / 1024 / 1024 / 1024 < clientConfig.getPlotMovementThreshold().getValue()) {
                            return true;
                        }
                    }
                    return false;
                });
        return !anyDiskNeed;
    }

    public List<Ipv4Address> getServersIpWithoutPlots() {
        for (ChiaServer chiaServer : chiaServer.getConnectedChiaServers()) {
            return chiaServer.getIpv4Addresses()
                    .stream()
                    .filter(ipv4Address -> {
                        ChiaAwesomeRestApi restApi = new ChiaAwesomeRestApi(ipv4Address.getAddress(), DEFAULT_PORT);
                        return restApi.isPlotNeed();
                    })
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public Ipv4Address getServerIpWhichNeedPlotTheMost(List<Ipv4Address> serversIp) throws URISyntaxException {
        Ipv4Address serverIpWhichNeedPlotTheMost = null;
        Integer maxPlotCount = 0;

        for (Ipv4Address serverIp : serversIp) {
            ChiaAwesomeRestApi restApi = new ChiaAwesomeRestApi(serverIp.getAddress(), DEFAULT_PORT);
            int howMany = restApi.howManyPlotsNeed();
            if (howMany > maxPlotCount) {
                maxPlotCount = howMany;
                serverIpWhichNeedPlotTheMost = serverIp;
            }
        }
        return serverIpWhichNeedPlotTheMost;
    }

    private Boolean checkDiskThreshold(Disk disk) {

        if (ThresholdType.PERCENT.equals(clientConfig.getPlotMovementThreshold().getType())) {
            if (disk.getUsedSpace() / disk.getTotalSpace() * 100 < clientConfig.getPlotMovementThreshold().getValue()) {
                return true;
            }
        }
        if (ThresholdType.GIB_SPACE.equals(clientConfig.getPlotMovementThreshold().getType())) {
            if (disk.getAvailableSpace() / 1024 / 1024 / 1024 < clientConfig.getPlotMovementThreshold().getValue()) {
                return true;
            }
        }
        return false;
    }
}
