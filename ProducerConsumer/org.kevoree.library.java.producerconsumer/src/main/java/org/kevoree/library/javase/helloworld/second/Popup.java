package org.kevoree.library.javase.helloworld.second;

import org.kevoree.annotation.ComponentType;
import org.kevoree.annotation.Input;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: gregory.nain
 * Date: 28/11/2013
 * Time: 17:43
 * To change this template use File | Settings | File Templates.
 */
@ComponentType
public class Popup {

    @Input
    public boolean pop(Object msg) {
        return JOptionPane.showConfirmDialog(null, msg,"Message Received", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.OK_OPTION;
    }



}
