/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.net.cmd;

import com.scg.domain.TimeCard;

/**
 * Command that adds a TimeCard to the server's list of TimeCards.

 * @author dixya
 */
public class AddTimeCardCommand extends AbstractCommand<TimeCard>{

    private static final long serialVersionUID = 1L;
    /** Construct an AddTimeCardCommand with a target.
     * @param target
*/
    public AddTimeCardCommand(TimeCard target){
        //this.target=target;
        super(target);
    }

    @Override
    public void execute() {
        
                       getReceiver().execute(this);

    }
    
}
