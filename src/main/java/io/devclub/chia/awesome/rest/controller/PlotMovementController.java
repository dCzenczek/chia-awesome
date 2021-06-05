package io.devclub.chia.awesome.rest.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/plots")
public class PlotMovementController {

    @GetMapping
    public boolean isPlotNeeded(){
        return false;
    }

    @GetMapping(value = "/needed")
    public int howManyPlotNeeded() {
        return 0;
    }

    @PostMapping(value = "/sendPlot")
    public void sendPlot(@RequestBody String plotName) {

    }


}
