package org.kevoree.library.javase.helloworld.second;

import org.kevoree.annotation.ComponentType;
import org.kevoree.annotation.Input;
import org.kevoree.annotation.Library;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: gregory.nain
 * Date: 28/11/2013
 * Time: 17:43
 * To change this template use File | Settings | File Templates.
 */

@ComponentType
@Library(name = "Java - Samples")
public class Popup {

    @Input
    public boolean pop(Object msg) {

        //Asks the user to answer yes or no to a message
        int response = JOptionPane.showConfirmDialog(null, msg,"Message Received", JOptionPane.YES_NO_CANCEL_OPTION);

        return  response == JOptionPane.OK_OPTION;
    }



}
