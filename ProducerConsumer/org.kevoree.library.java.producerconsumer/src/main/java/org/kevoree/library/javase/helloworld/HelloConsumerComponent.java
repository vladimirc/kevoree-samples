package org.kevoree.library.javase.helloworld;

import org.kevoree.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * User: gnain
 * Date: 27/10/11
 * Time: 13:43
 */

@ComponentType
@Library(name = "Library")
public class HelloConsumerComponent {

    @Start
    public void startComponent() {
        System.out.println("Consumer:: Start");
    }

    @Stop
    public void stopComponent() {
        System.out.println("Consumer:: Stop");
    }

    @Update
    public void updateComponent() {
        System.out.println("Consumer:: Update");
    }

    @Input
    public void consumeHello(Object o) {
        System.out.println("Consumer:: Received " + o.toString());
        if(o instanceof String) {
            String msg = (String)o;
            System.out.println("HelloConsumer received: " + msg);
        }
    }

}
