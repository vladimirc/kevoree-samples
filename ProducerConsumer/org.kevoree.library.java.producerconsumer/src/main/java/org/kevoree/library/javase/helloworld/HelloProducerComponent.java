package org.kevoree.library.javase.helloworld;

import org.kevoree.annotation.*;
import org.kevoree.api.Port;

import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: gnain
 * Date: 27/10/11
 * Time: 13:42
 */

@ComponentType
public class HelloProducerComponent implements HelloProductionListener {

    @Output
    private org.kevoree.api.Port produce;

    private HelloProducerThread producer;

    @Param
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

    @Input
    public void helloProduced(Object helloValue) {

        produce.call(helloValue);

    }
}
