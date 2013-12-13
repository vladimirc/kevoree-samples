package org.kevoree.sample.helloworld;

import org.kevoree.annotation.*;

import java.lang.management.ManagementFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by duke on 08/12/2013.
 */
@ComponentType
public class ConsoSensor implements Runnable {

    @Param(defaultValue = "2000")
    Long time;

    ScheduledExecutorService executorService;
    ScheduledFuture future;

    @Start
    public void start() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        update();
    }

    @Stop
    public void stop() {
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }

    @Update
    public void update() {
        if (future != null) {
            future.cancel(true);
        }
        future = executorService.scheduleAtFixedRate(this,0, time, TimeUnit.MILLISECONDS);
    }

    public String getCurrentLoad() {
        return ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage() + "";
    }

    @Override
    public void run() {
        System.out.println("Current load : " + getCurrentLoad());
    }

    @Input
    public String conso(Object o) {
        return getCurrentLoad();
    }

}
