package org.kevoree.sample;

import org.kevoree.ComponentInstance;
import org.kevoree.ContainerNode;
import org.kevoree.ContainerRoot;
import org.kevoree.annotation.*;
import org.kevoree.api.Context;
import org.kevoree.api.ModelService;
import org.kevoree.api.handler.UpdateCallback;
import org.kevoree.cloner.DefaultModelCloner;
import org.kevoree.kevscript.KevScriptEngine;
import org.kevoree.log.Log;
import org.kevoree.modeling.api.ModelCloner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by duke on 05/12/2013.
 */
@ComponentType
@Library(name = "Java :: Web")
public class Scaler implements Runnable {

    private Thread current = null;

    @Param(defaultValue = "1", optional = true)
    Integer target = null;

    @KevoreeInject
    ModelService modelService;

    @KevoreeInject
    Context context;

    @Start
    public void start() {
        current = new Thread(this);
        current.start();
    }

    @Stop
    public void stop() {
        current.stop();
    }

    private static final String propName = "http_port";
    private static final String typeName = "NanoBlogServer";

    @Override
    public void run() {
        while (true) {

            try {
                Thread.sleep(5000);

                //Collect all instance of NanoServer
                ModelCloner cloner = new DefaultModelCloner();
                ContainerRoot model = cloner.clone(modelService.getCurrentModel().getModel());

                //collect used ports
                List<String> ports = new ArrayList<String>();
                List<String> names = new ArrayList<String>();

                for (ContainerNode node : model.getNodes()) {
                    List<Object> selected = node.selectByQuery("components[typeDefinition.name = NanoBlogServer]");
                    for (Object loop : selected) {
                        ComponentInstance instance = (ComponentInstance) loop;
                        if (instance.getDictionary() != null) {
                            if (instance.getDictionary().findValuesByID(propName) != null) {
                                ports.add(instance.getDictionary().findValuesByID(propName).getValue());
                                names.add(instance.getName());
                            }
                        } else {
                            if (instance.getTypeDefinition().getDictionaryType().findAttributesByID(propName).getDefaultValue() != null) {
                                ports.add(instance.getTypeDefinition().getDictionaryType().findAttributesByID(propName).getDefaultValue());
                                names.add(instance.getName());
                            }
                        }
                    }
                }

                if (ports.size() != target) {

                    KevScriptEngine engine = new KevScriptEngine();
                    //reactions
                    if (ports.size() > target) {
                        Log.info("To much instances, drop some");
                        while (names.size() > target) {
                            String toDrop = names.get(0);
                            names.remove(toDrop);
                            //TODO remove component here
                            Log.info("Scaler drop " + toDrop);
                        }
                    } else {
                        while (ports.size() < target) {
                            Log.info("No enough instances, add some");
                            Random r = new Random();
                            Integer basePort = 8010;
                            while (ports.contains(basePort.toString())) {
                                basePort++;
                            }
                            String newName = "backend_" + Math.abs(r.nextInt());
                            //TODO add component here
                            //TODO uncoment this line after adding the component
                            //engine.execute("set " + context.getNodeName() + "." + newName + ".http_port = \"" + basePort.toString() + "\"", model);
                            ports.add(basePort.toString());
                        }
                    }
                    Log.info("Nb instances is fine now :-)");
                    modelService.update(model, new UpdateCallback() {
                        @Override
                        public void run(Boolean applied) {
                            Log.info("Scaler update system");
                        }
                    });

                } else {
                    Log.info("Everything is fine ! nothing to do ...");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
