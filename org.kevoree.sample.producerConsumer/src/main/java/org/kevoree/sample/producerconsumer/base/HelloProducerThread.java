package org.kevoree.sample.producerconsumer.base;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: gnain
 * Date: 27/10/11
 * Time: 14:33
 */
public class HelloProducerThread extends Thread {

    private boolean stopped = false;
    private long delay = 2000;
    private ArrayList<HelloProductionListener> listeners = new ArrayList<HelloProductionListener>();
    private int time = 0;

    public HelloProducerThread() {}

    public HelloProducerThread(long delay) {
        this.delay = delay;
    }

    public void addHelloProductionListener(HelloProductionListener lst) {
        listeners.add(lst);
    }

    public void halt() {
        stopped = true;
    }

    public boolean isStopped(){return stopped;}

    public void run() {
        while(!stopped) {
            produceHello();
            time ++;
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void produceHello() {
        for(final HelloProductionListener listener : listeners) {
            new Thread(new Runnable(){
                public void run() {listener.helloProduced("Bonjour time " + time);}
            }).start();
        }
    }

}
