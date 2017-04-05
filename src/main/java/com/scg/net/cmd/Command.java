/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.net.cmd;

import com.scg.net.server.CommandProcessor;
import java.io.Serializable;

/**
 * The superclass of all Command objects, implements the command role in the Command design pattern.
 * @author dixya
 * @param <T> Generic constant
 */
public interface Command<T> extends Serializable{
    /** The method called by the receiver. */

    void execute();
    
    /** Gets the CommandProcessor receiver for this Command.
     * @return      the receiver for this Command.
     */

    CommandProcessor getReceiver();
    
    /** Return the target of this Command.
     * @return the target.
*/
    T getTarget();
    
    /** Set the CommandProcessor that will execute this Command.
     * @param  receiver  the receiver for this Command.
*/
    void setReceiver(CommandProcessor receiver);
    
}
