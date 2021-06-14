package io.devclub.chia.awesome.api.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ChiaAwesomeRestApi {

    private final String ipAddress;
    private final String port;

    public ChiaAwesomeRestApi(String ipAddress,
                              String port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public boolean isPlotNeed() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://" + ipAddress + ":" + port + ChiaAwesomeUrlUtil.GET_PLOT_IS_NEED;
        ResponseEntity<Boolean> isPlotNeedResponse = restTemplate
                .getForEntity(url, Boolean.class);
        if (isPlotNeedResponse.getStatusCode().is2xxSuccessful()) {
            return isPlotNeedResponse.getBody();
        }
        return false;
    }

    public int howManyPlotsNeed() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://" + ipAddress + ":" + port + ChiaAwesomeUrlUtil.GET_PLOT_HOW_MANY;
        ResponseEntity<Integer> howManyPlotsNeed = restTemplate
                .getForEntity(url, Integer.class);
        if (howManyPlotsNeed.getStatusCode().is2xxSuccessful()) {
            return howManyPlotsNeed.getBody();
        }
        return 0;
    }
}
