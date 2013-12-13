package org.kevoree.sample.breakdown;

import org.kevoree.annotation.*;
import org.kevoree.api.Context;
import org.kevoree.api.KevScriptService;
import org.kevoree.api.ModelService;
import org.kevoree.log.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by duke on 06/12/2013.
 */

@ComponentType
public class BreakdownAlone implements Runnable {

    private ScheduledExecutorService service = null;

    @Param(defaultValue = "2000")
    Integer time;               //Time to live in ms

    @KevoreeInject
    ModelService modelService;
    @KevoreeInject
    Context context;
    @KevoreeInject
    KevScriptService kevScriptService;

    private ScheduledFuture currentTimeTask;

    @Override
    public void run() {
        System.out.println("Feeling alone, should suppress myself...");
        System.out.println("I'm " + context.getInstanceName());
        //TODO here put code to uninstall component
    }

    @Start
    public void start() {
        service = Executors.newSingleThreadScheduledExecutor();
        currentTimeTask = service.schedule(this, time, TimeUnit.MILLISECONDS);
    }

    @Stop
    public void stop() {
        service.shutdownNow();
        service = null;

    }

    @Input
    public void input(Object o) {
        Log.info("A friend! delay my suppression");
        if (currentTimeTask != null) {
            currentTimeTask.cancel(true);
        }
        currentTimeTask = service.schedule(this, time, TimeUnit.MILLISECONDS);
    }


}
