package org.kevoree.sample;

import org.kevoree.annotation.ComponentType;
import org.kevoree.annotation.Start;
import org.kevoree.annotation.Stop;

/**
 * Created by duke on 06/12/2013.
 */

@ComponentType
public class BreakdownAlone implements Runnable {
    @Override
    public void run() {
        //TODO
    }

    @Start
    public void start() {
        System.out.println("TODO :-)");
        //TODO
    }

    @Stop
    public void stop() {

    }

}
