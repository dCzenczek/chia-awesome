package io.devclub.chia.awesome.rest.controller;

import io.devclub.chia.awesome.rest.request.Plot;
import io.devclub.chia.awesome.socket.plot.receiver.PlotReceiver;
import io.devclub.chia.awesome.socket.plot.receiver.SocketChannelSingleton;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Log4j2
@RestController
@RequestMapping("/plots")
public class PlotMovementController {

    @GetMapping
    public boolean isPlotNeeded(){
        log.debug("New plot is not needed");
        return false;
    }

    @GetMapping(value = "/needed")
    public int howManyPlotNeeded() {
        return 0;
    }

    @PostMapping(value = "/acceptPlot")
    public boolean acceptPlot(@RequestBody Plot plot) throws IOException {

        // TODO: 05/06/2021
        String pathToStorePlot = calculateWhereToPutPlot();
        PlotReceiver plotReceiver = new PlotReceiver();

        // Singleton just for test, it should be on new Thread i guess
        plotReceiver.readFileFromSocketChannel(SocketChannelSingleton.getSocketChannel(), pathToStorePlot);


        // return OK saving on another thread.
        return checkPlotSize(plot);
    }

    public boolean checkPlotAcceptation() {
        // TODO: 05/06/2021 check available size on disk to make sure we can store plot
        return false;
    }

    public String calculateWhereToPutPlot() {
        // TODO: 05/06/2021 calculate best Optimal disc to put plot
        return "";
    }

    public boolean checkPlotSize(Plot plot) {
        // TODO: 05/06/2021 check if plot is the same size local and on remote
        return false;
    }


}
