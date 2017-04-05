/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.net.cmd;

import com.scg.net.server.CommandProcessor;

/**
 *
 * @author dixya
 * @param <T>
 */
public abstract class AbstractCommand<T> implements Command<T>{

    private static final long serialVersionUID = 1L;
    private T target;
     transient CommandProcessor receiver;
    /** Construct an AbstractCommand without a target; called from subclasses.
*/
    public AbstractCommand(){
        
    }
    /** Construct an AbstractCommand with a target; called from subclasses.
     * @param target The target of this Command.
*/
    public AbstractCommand(T target){
        this.target=target;
        //super(target);
    }

  
    //public abstract  void execute();

    /**
     * Gets the CommandProcessor receiver for this Command.
     * @return the receiver for this Command.
     */
    @Override
    public CommandProcessor getReceiver() {
        return receiver;
    }

/**
 * Return the target of this Command.
 * @return  the target.
 */
    @Override
    public final T getTarget(){
        return target;
    }
    /**
     * Set the CommandProcessor that will execute this Command.
     * @param receiver the receiver for this Command.
     */
    @Override
    public void setReceiver(CommandProcessor receiver) {
        this.receiver=receiver;
    }
    /**
     * A string representation of this command. 
     * @return 
     */
    @Override
    public String toString(){
        String info="TARGET="+target+ "Receiver="+receiver;
        return info;
    }

    
}
