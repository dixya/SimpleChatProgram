/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.net.cmd;

import com.scg.domain.ClientAccount;

/**
 * The command to add a Client to a list maintained by the server.
 * @author dixya
 */
public class AddClientCommand extends AbstractCommand<ClientAccount>{

    private static final long serialVersionUID = 1L;
    
    /** Construct an AddClientCommand with a target.
     * @param target The target of this Command.
*/
    public AddClientCommand(ClientAccount target){
        super(target);
    }

    @Override
    public void execute() {
        System.out.println("clientside receiver="+receiver);
        receiver.execute(this);
        
            
        
    }
    
}
