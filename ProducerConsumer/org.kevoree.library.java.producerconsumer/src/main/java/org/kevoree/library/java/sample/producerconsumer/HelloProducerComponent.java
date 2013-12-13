package org.kevoree.library.java.sample.producerconsumer;

import org.kevoree.annotation.*;


/**
 * Created by IntelliJ IDEA.
 * User: gnain
 * Date: 27/10/11
 * Time: 13:42
 */
@ComponentType
@Library(name = "Java - Samples")
public class HelloProducerComponent implements HelloProductionListener {

    @Output
    private org.kevoree.api.Port helloProducedPort;

    private HelloProducerThread producer;

    @Param(defaultValue = "2000")
    private int delay = 2000;

    @Start
    public void startComponent() {
        if (producer == null || producer.isStopped()) {
            producer = new HelloProducerThread(delay);
            producer.addHelloProductionListener(this);
            producer.start();
        }
    }

    @Stop
    public void stopComponent() {
        if (producer != null) {
            producer.halt();
        }
    }

    @Update
    public void updateComponent() {
        stopComponent();
        startComponent();
    }

    public void helloProduced(Object helloValue) {

        helloProducedPort.send(helloValue);

    }
}
