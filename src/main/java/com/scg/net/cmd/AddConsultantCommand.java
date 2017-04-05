/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.net.cmd;

import com.scg.domain.Consultant;

/**
 * The command to add a Consultant to a list maintained by the server.
 * @author dixya
 */
public class AddConsultantCommand extends AbstractCommand<Consultant>{

    private static final long serialVersionUID = 1L;
    /** Construct an AddConsultantCommand with a target.
     * @param target The target of this Command.
*/
    public AddConsultantCommand(Consultant target){
        //this.target=target;
        super(target);
    }

    @Override
    public void execute() {       
                        receiver.execute(this);

        
    }
    
}
