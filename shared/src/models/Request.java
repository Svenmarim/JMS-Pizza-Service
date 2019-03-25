package models;

import javax.jms.Destination;
import java.io.Serializable;

public class Request implements Serializable {
    private String name;
    private String address;
    private String description;
    private Destination destination;

    public Request(){

    }

    public Request(String name, String address, String description) {
        this.name = name;
        this.address = address;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public Destination getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "Order: " + getDescription() + "; " +
                "Name: " + getName() + "; " +
                "Address: " + getAddress() + ";";
    }
}
