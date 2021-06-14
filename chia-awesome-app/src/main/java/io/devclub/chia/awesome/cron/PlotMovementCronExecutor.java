package io.devclub.chia.awesome.cron;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

@Component
public class PlotMovementCronExecutor {

    private int threadsNumber = 10;
    private ExecutorService executorService;

    @PostConstruct
    private void init() {
        executorService = Executors.newFixedThreadPool(threadsNumber);
    }

    public void start(Runnable runnable) {
        try {
            executorService.execute(runnable);
        } catch (RejectedExecutionException e) {
            init();
            executorService.execute(runnable);
        }
    }
}
