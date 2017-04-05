/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.net.cmd;

/**
 * This Command will cause the CommandProcessor to shutdown the server.
 *
 * @author dixya
 */
public class ShutdownCommand extends AbstractCommand<Void> {

    private static final long serialVersionUID = 1L;

    /**
     * Construct an ShutdownCommand
     */
    public ShutdownCommand() {
        super();
    }

    /**
     * The method called by the receiver and implemented by subclasses.
     */
    @Override
    public void execute() {
        getReceiver().execute(this);
    }

}
