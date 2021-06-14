package io.devclub.chia.awesome.cron;

import io.devclub.chia.awesome.ChiaAwesomeClient;
import io.devclub.chia.awesome.api.rest.ChiaAwesomeRestApi;
import io.devclub.chia.awesome.api.rest.model.ChiaServer;
import io.devclub.chia.awesome.api.rest.model.Ipv4Address;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Component
public class PlotMovementCronProcessor {

    private static final String DEFAULT_PORT = "8080";
    private final List<ChiaServer> connectedChiaServers;

    public PlotMovementCronProcessor(ChiaAwesomeClient chiaAwesomeClient) {
        this.connectedChiaServers = chiaAwesomeClient.getConnectedChiaServers();
    }

    public void checkAndSendPlotIfNeeded() {
        if (isPlotMovementNeeded()) {
            log.info("Plot movement needed, getting servers that need plots");
            List<Ipv4Address> serversIp = getServersIpWithoutPlots();
            log.info("Get server which need plot the most");
            try {
                Ipv4Address serverIpWhichNeedPlotTheMost = getServerIpWhichNeedPlotTheMost(serversIp);

            } catch (URISyntaxException e) {
                // TODO: 05/06/2021 handle this exception
                e.printStackTrace();
            }
        }
    }

    public boolean isPlotMovementNeeded() {
        log.info("Checking if plot movement is needed");
        return false;
    }

    public List<Ipv4Address> getServersIpWithoutPlots() {
        for (ChiaServer chiaServer : connectedChiaServers) {
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
}
