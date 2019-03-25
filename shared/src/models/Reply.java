package models;

import java.io.Serializable;

public class Reply implements Serializable {
    private Request request;
    private double price;
    private int arrivalMinutes;

    public Reply(){

    }

    public Reply(Request request, double price, int arrivalMinutes) {
        this.request = request;
        this.price = price;
        this.arrivalMinutes = arrivalMinutes;
    }

    public Request getRequest() {
        return request;
    }

    public double getPrice() {
        return price;
    }

    public int getArrivalMinutes() {
        return arrivalMinutes;
    }

    @Override
    public String toString() {
        return "Order: " + request.getDescription() + "\n" +
                "Name: " + request.getName() + "\n" +
                "Address: " + request.getAddress() + "\n" +
                "Price: " + price + "\n" +
                "Estimated duration: " + arrivalMinutes + "min.";
    }
}
