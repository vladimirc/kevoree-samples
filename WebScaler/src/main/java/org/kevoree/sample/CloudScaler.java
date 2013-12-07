package org.kevoree.sample;

import org.kevoree.ComponentInstance;
import org.kevoree.ContainerNode;
import org.kevoree.ContainerRoot;
import org.kevoree.annotation.*;
import org.kevoree.api.Context;
import org.kevoree.api.KevScriptService;
import org.kevoree.api.ModelService;
import org.kevoree.cloner.DefaultModelCloner;
import org.kevoree.kevscript.KevScriptEngine;
import org.kevoree.log.Log;
import org.kevoree.modeling.api.ModelCloner;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by duke on 05/12/2013.
 */
@ComponentType
@Library(name = "Java :: Web")
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

    ModelCloner cloner = new DefaultModelCloner();

    @Override
    public void run() {
        try {
            //get and clone
            ContainerRoot root = cloner.clone(modelService.getCurrentModel().getModel());
            ContainerNode currentNode = root.findNodesByID(context.getNodeName());
            if (targetRedondency < currentNode.getHosts().size() && !currentNode.getHosts().isEmpty()) {
                Log.info("remove child node");
                kevScriptService.execute("remove " + currentNode.getName() + "." + currentNode.getHosts().get(0).getName(), root);
            } else {
                if (targetRedondency > currentNode.getHosts().size()) {
                    Log.info("Add child node");
                    kevScriptService.execute("add " + currentNode.getName() + ".child" + System.currentTimeMillis() + " : JavaNode ", root);
                    //TODO add as well web servers
                }
            }

            modelService.update(root,null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
