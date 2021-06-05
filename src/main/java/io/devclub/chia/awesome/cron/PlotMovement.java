package io.devclub.chia.awesome.cron;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class PlotMovement {

    private final PlotMovementCronExecutor plotMovementCronExecutor;

    private final PlotMovementCronProcessor plotMovementCronProcessor;

    @Autowired
    public PlotMovement(PlotMovementCronExecutor plotMovementCronExecutor,
                                         PlotMovementCronProcessor plotMovementCronProcessor) {
        this.plotMovementCronExecutor = plotMovementCronExecutor;
        this.plotMovementCronProcessor = plotMovementCronProcessor;
    }

    @Scheduled(cron = "* */1 * * * *")
    public void startScheduledCronMovement() throws Exception {
        log.debug("Start scheduled cron movement");
        plotMovementCronExecutor.start(plotMovementCronProcessor::checkAndSendPlotIfNeeded);
    }

}

