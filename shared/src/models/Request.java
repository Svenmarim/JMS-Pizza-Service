package models;

import java.io.Serializable;

public class Request implements Serializable {
    private String name;
    private String address;
    private String description;

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

    @Override
    public String toString() {
        return "Order: " + getDescription() + "\n" +
                "Name: " + getName() + "\n" +
                "Address: " + getAddress();
    }
}
