/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.net.cmd;

import com.scg.net.server.CommandProcessor;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The command to disconnect, this command has no target.
 * @author dixya
 */
public class DisconnectCommand extends AbstractCommand<Void>{

    private static final long serialVersionUID = 1L;
    /** Construct an DisconnectCommand.
*/
    public DisconnectCommand(){
        super();
    }

    @Override
    public void execute() {
        try {
            receiver.execute(this);
        } catch (IOException ex) {
            Logger.getLogger(DisconnectCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
