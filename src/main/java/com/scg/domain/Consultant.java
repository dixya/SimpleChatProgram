/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.domain;

import com.scg.util.Name;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A consultant.
 *
 * @author dixya
 */
public class Consultant implements Comparable<Consultant>, Serializable {

    private static final long serialVersionUID = 1L;

    private final String ConsultantFirstName;
    private final String ConsultantMiddleName;
    private final String ConsultantLastName;
        /** This class' logger. */
    private static final Logger log=LoggerFactory.getLogger(Consultant.class);
    /**
     * Creates a new instance of Consultant.
     *
     * @param name
     */
    public Consultant(Name name) {
        this.ConsultantFirstName = name.getFirstName();
        this.ConsultantMiddleName = name.getMiddleName();
        this.ConsultantLastName = name.getLastName();
        
    }

    /**
     * Getter for property name.
     *
     * @return consultant name
     */
    public Name getName() {
        Name ConsultantName = new Name(ConsultantLastName, ConsultantFirstName, ConsultantMiddleName);
        return ConsultantName;

    }

    /**
     * Returns the string representation of the consultant's name.
     *
     * @return string value.
     */
    @Override
    public String toString() {
        StringBuilder ConsName = new StringBuilder();
        ConsName.append(ConsultantLastName);
        ConsName.append(",");
        ConsName.append(ConsultantFirstName);
        if (ConsultantMiddleName != null) {
            ConsName.append(" ");
            ConsName.append(ConsultantMiddleName);

        }
        return ConsName.toString();
    }

    @Override
    public int compareTo(Consultant t) {
        //return name.compareTo(t.name);
        int consultantResult = (this.getName()).compareTo(t.getName());
        return consultantResult;
    }

    /**
     * Writes this object's proxy to stream.
     * @return object.
     */
    private Object writeReplace() {
        final String msg=String.format("Serialized consultant=%s", getName());
        log.info(msg);
        return new SerializationProxy(this);
    }

    /**
     * Throws an InvalidObjectException a serialization proxy must be used
     * @param ois not used
     * @throws InvalidObjectException always.
     */
    private void readObject(ObjectInputStream ois)
      throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }

    /**
     * Implementation of serialization proxy.
     */
    private static class SerializationProxy implements Serializable {

        private static final long serialVersionUID = 1L;

        private final Name consultantName;
        private final String consultantFirstName;
        private final String consultantMiddleName;
        private final String consultantLastName;



        /**
         * Constructor that logs consultant name.
         * @param consultant 
         */
        SerializationProxy(final Consultant consultant) {
            this.consultantName = consultant.getName();
            this.consultantFirstName=consultantName.getFirstName();
            this.consultantMiddleName=consultantName.getMiddleName();
            this.consultantLastName=consultantName.getLastName();

        }

        /***
         * Creates the proxied consultant.
         * @return new instance of the proxied consultant.
         */
        private Object readResolve() {
            final String msg=String.format("Deserialized consultant=%s", consultantName);
            log.info(msg);
            return new Consultant(consultantName);
        }
    }

}
