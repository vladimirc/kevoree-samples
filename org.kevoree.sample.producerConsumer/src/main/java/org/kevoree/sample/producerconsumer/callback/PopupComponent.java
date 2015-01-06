package org.kevoree.sample.producerconsumer.callback;

import org.kevoree.annotation.ComponentType;
import org.kevoree.annotation.Input;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: gregory.nain
 * Date: 28/11/2013
 * Time: 17:43
 */

@ComponentType
public class PopupComponent {

    @Input
    public boolean pop(Object msg) {

        //Asks the user to answer yes or no to a message
        int response = JOptionPane.showConfirmDialog(null, msg,"Message Received", JOptionPane.YES_NO_CANCEL_OPTION);

        return  response == JOptionPane.OK_OPTION;
    }



}
