/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.net.cmd;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Creates invoices.
 * @author dixya
 */
public class CreateInvoicesCommand extends AbstractCommand<LocalDate> {

    private static final long serialVersionUID = 1L;

    /**
     * Construct a CreateInvoicesCommand with a target month, which should be
     * obtained by getting the desired month constant from Calendar.
     *
     * @param target
     */
    public CreateInvoicesCommand(LocalDate target) {
        //this.target=target;
        super(target);

    }

    @Override
    public void execute() {
        try {
            receiver.execute(this);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CreateInvoicesCommand.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
