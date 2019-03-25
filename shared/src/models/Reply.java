package models;

import javax.jms.Destination;
import java.io.Serializable;

public class Reply implements Serializable {
    private Request request;
    private double price;
    private int arrivalMinutes;
    private String pizzaPlace;
    private Destination destination;

    public Reply(){

    }

    public Reply(Request request, double price, int arrivalMinutes, String pizzaPlace) {
        this.request = request;
        this.price = price;
        this.arrivalMinutes = arrivalMinutes;
        this.pizzaPlace = pizzaPlace;
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

    public String getPizzaPlace() {
        return pizzaPlace;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public Destination getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "Order: " + request.getDescription() + "; " +
                "Name: " + request.getName() + "; " +
                "Address: " + request.getAddress() + "; " +
                "Price: " + price + "; " +
                "Estimated duration: " + arrivalMinutes + "min.; " +
                "Pizza place: " + pizzaPlace + ";";
    }
}
