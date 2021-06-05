package io.devclub.chia.awesome.cron;

import io.devclub.chia.awesome.rest.request.Ipv4Address;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

@Log4j2
@Component
public class PlotMovementCronProcessor {



    public void checkAndSendPlotIfNeeded() {
        if(isPlotMovementNeeded()) {
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
        // TODO: 05/06/2021 prepare logic
        return false;
    }

    public List<Ipv4Address> getServersIpWithoutPlots() {
        // TODO: 05/06/2021 uruchominie serwisu z IPikami + wyslanie geta (http://IP/plots)
        return Collections.emptyList();
    }

    public Ipv4Address getServerIpWhichNeedPlotTheMost(List<Ipv4Address> serversIp) throws URISyntaxException {

        RestTemplate restTemplate = new RestTemplate();
        Ipv4Address serverIpWhichNeedPlotTheMost = null;
        Integer maxPlotCount = 0;

        for (Ipv4Address serverIp : serversIp) {
            final String baseUrl = "http://" + serverIp +  ":8080/plots/needed";
            URI uri = new URI(baseUrl);
            ResponseEntity<Integer> result = restTemplate.getForEntity(uri, Integer.class);
            if(result.getBody() > maxPlotCount); {
                maxPlotCount = result.getBody();
                serverIpWhichNeedPlotTheMost = serverIp;
            }
        }
        return serverIpWhichNeedPlotTheMost;
    }
}
