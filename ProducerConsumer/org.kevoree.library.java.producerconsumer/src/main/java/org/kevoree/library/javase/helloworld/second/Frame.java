package org.kevoree.library.javase.helloworld.second;

import org.kevoree.annotation.ComponentType;
import org.kevoree.annotation.Output;
import org.kevoree.api.Callback;
import org.kevoree.api.Port;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: gregory.nain
 * Date: 28/11/2013
 * Time: 17:43
 * To change this template use File | Settings | File Templates.
 */

@ComponentType
public class Frame {

    private JFrame mainFrame;
    private JTextField textField;
    private JButton send;

    @Output
    private Port textEntered;


    public Frame() {
        mainFrame = new JFrame("Enter your text:");
        mainFrame.getContentPane().setLayout(new FlowLayout());
        textField = new JTextField(20);
        send = new JButton("Send");

        send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textEntered.call(textField.getText(), new Callback() {
                    @Override
                    public void run(Object result) {
                        JOptionPane.showMessageDialog(mainFrame, ((Boolean)result?"Ack":"NACK"), "Message Read!", JOptionPane.INFORMATION_MESSAGE);
                    }
                });
            }
        });

        mainFrame.getContentPane().add(textField);
        mainFrame.getContentPane().add(send);

        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }



}
