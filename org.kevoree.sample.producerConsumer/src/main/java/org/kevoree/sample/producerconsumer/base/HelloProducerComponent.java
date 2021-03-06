package org.kevoree.sample.producerconsumer.base;

import org.kevoree.annotation.*;
import org.kevoree.api.Callback;
import org.kevoree.api.CallbackResult;


/**
 * Created by IntelliJ IDEA.
 * User: gnain
 * Date: 27/10/11
 * Time: 13:42
 */
@ComponentType
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

    public void helloProduced(String helloValue) {
        helloProducedPort.send(helloValue, new Callback() {
            public void onSuccess(CallbackResult callbackResult) {

            }

            public void onError(Throwable throwable) {

            }
        });
    }
}
