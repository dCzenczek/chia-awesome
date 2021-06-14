package io.devclub.chia.awesome.rest.controller;

import io.devclub.chia.awesome.api.rest.model.Plot;
import io.devclub.chia.awesome.api.rest.model.request.PlotMovementRequest;
import io.devclub.chia.awesome.socket.plot.receiver.PlotReceiver;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Log4j2
@RestController
@RequestMapping("/plots")
public class PlotMovementController {

    @GetMapping(value = "/isNeed")
    public boolean isPlotNeeded(){
        log.debug("New plot is not needed");
        return false;
    }

    @GetMapping(value = "/howMany")
    public int howManyPlotNeeded() {
        return 0;
    }

    @PostMapping(value = "/acceptPlot")
    public boolean acceptPlot(@RequestBody PlotMovementRequest plotMovementRequest) throws IOException {
        //First check if new plot is needed
        if (!checkPlotAcceptation()) {
            return false;
        }
        //Calculate optimal place to store new plot and get full path
        String pathToStorePlot = calculateWhereToPutPlot(plotMovementRequest.getPlot());
        PlotReceiver plotReceiver = new PlotReceiver(plotMovementRequest.getSocketPort(),pathToStorePlot);
        plotReceiver.run();

        // return OK when no Exception and socket channel on port is open
        return true;
    }

    public boolean checkPlotAcceptation() {
        // TODO: 05/06/2021 check available size on disk to make sure we can store plot
        return false;
    }

    public String calculateWhereToPutPlot(Plot plot) {
        // TODO: 05/06/2021 calculate best Optimal disc to put plot
        return "";
    }

    public boolean checkPlotSize(Plot plot) {
        // TODO: 05/06/2021 check if plot is the same size local and on remote
        return false;
    }



}
