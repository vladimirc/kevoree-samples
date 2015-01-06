package org.kevoree.sample.cloudscaler;

import org.kevoree.annotation.*;
import org.kevoree.api.Context;
import org.kevoree.api.KevScriptService;
import org.kevoree.api.ModelService;
import org.kevoree.api.handler.UpdateCallback;
import org.kevoree.log.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by duke on 05/12/2013.
 */
@ComponentType
public class CloudScaler implements Runnable {

    ScheduledExecutorService service = null;

    @KevoreeInject
    ModelService modelService;

    @KevoreeInject
    Context context;

    @Param
    Integer targetRedondency = 5;

    @Start
    public void start() {
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(this, 1000, 3000, TimeUnit.MILLISECONDS);
    }

    @Stop
    public void stop() {
        service.shutdownNow();
    }

    @KevoreeInject
    KevScriptService kevScriptService;

    @Override
    public void run() {
        try {
            //get and clone

            org.kevoree.ContainerRoot root =  modelService.getCurrentModel().getModel();
            org.kevoree.ContainerNode currentNode = root.findNodesByID(context.getNodeName());
            if (targetRedondency < currentNode.getHosts().size() && !currentNode.getHosts().isEmpty()) {
                Log.info("remove child node");
                modelService.submitScript("remove " + currentNode.getName() + "." + currentNode.getHosts().get(0).getName(), new UpdateCallback() {
                    public void run(Boolean aBoolean) {

                    }
                });
            } else {
                if (targetRedondency > currentNode.getHosts().size()) {
                    Log.info("Add child node");
                    modelService.submitScript("add " + currentNode.getName() + ".child" + System.currentTimeMillis() + " : JavaNode ", new UpdateCallback() {
                        public void run(Boolean aBoolean) {

                        }
                    });
                    //TODO add as well web servers
                }
            }

            modelService.update(root,null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
