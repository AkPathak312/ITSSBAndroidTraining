package com.example.itssbandroidtraining;

public class FlightData {
    String flightNumber;
    Double price;
    String time;
    String aircraft;

    public FlightData(String flightNumber, Double price, String time, String aircraft) {
        this.flightNumber = flightNumber;
        this.price = price;
        this.time = time;
        this.aircraft = aircraft;
    }
}
