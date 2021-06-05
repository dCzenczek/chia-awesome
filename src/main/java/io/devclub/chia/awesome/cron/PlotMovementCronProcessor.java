package io.devclub.chia.awesome.cron;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class PlotMovementCronProcessor {

    public void checkAndSendPlotIfNeeded() {
        if(isPlotMovementIsNeeded()) {

        }


        log.debug("Check server if the need plot");
    }

    public boolean isPlotMovementIsNeeded() {
        log.debug("Checking if plot movement is needed");
        // TODO: 05/06/2021 prepare logic
        return false;
    }

}
