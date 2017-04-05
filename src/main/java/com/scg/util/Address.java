/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.util;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author dixya
 */
public class Address implements Comparable<Address>,Serializable{

    private final String streetNumber;
    private final String city;
    private final StateCode state;
    private final String postalCode;

    /**
     * Construct an Address.
     *
     * @param streetNumber
     * @param city
     * @param state
     * @param postalCode
     */
    public Address(String streetNumber, String city, StateCode state, String postalCode) {
        this.streetNumber = streetNumber;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.streetNumber);
        hash = 83 * hash + Objects.hashCode(this.city);
        hash = 83 * hash + Objects.hashCode(this.state);
        hash = 83 * hash + Objects.hashCode(this.postalCode);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Address other = (Address) obj;
        if (!Objects.equals(this.streetNumber, other.streetNumber)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.postalCode, other.postalCode)) {
            return false;
        }
        if (this.state != other.state) {
            return false;
        }
        return true;
    }

    /**
     * Gets the city for this address.
     *
     * @return city in string format.
     */
    public String getCity() {
        return city;

    }

    /**
     * Gets the postal code for this address.
     *
     * @return string value of postal code.
     */
    public String getPostalCode() {
        return postalCode;

    }

    /**
     * Get the state for this address.
     *
     * @return state code.
     */
    public StateCode getState() {
        return state;

    }

    /**
     * Get the street number for this address.
     *
     * @return street number in string format.
     */
    public String getStreetNumber() {
        return streetNumber;

    }

    /**
     * Prints this address in the form: street number, city, state postal code
     *
     * @return String
     */
    @Override
    public String toString() {
        //return String.format(locale.US,"%s%n%s,%s %s", streetNumber,city,state,postalCode);
        StringBuilder sb = new StringBuilder();
        sb.append("Street Number:");
        sb.append(streetNumber);
        sb.append("\nCity: ");
        sb.append(city);
        sb.append("\nState:  ");
        sb.append(state);
        sb.append("\nPostal code: ");
        sb.append(postalCode);
        return sb.toString();
    }

    @Override
    public int compareTo(Address t) {
        int StreetResult = this.streetNumber.compareTo(t.streetNumber);
        if (StreetResult != 0) {
            return StreetResult;
        }
        int CityResult = this.city.compareTo(t.city);
        if (CityResult != 0) {
            return CityResult;
        }
        int StateResult = this.state.compareTo(t.state);
        if (StateResult != 0) {
            return StateResult;
        }

        return this.postalCode.compareTo(t.postalCode);

    }

}
