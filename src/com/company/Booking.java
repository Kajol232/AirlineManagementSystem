package com.company;

public class Booking {
    private String nameOfPassenger;
    private String typeOfFlight;
    private int classOfFlight;
    private String destination;
    private String departure;
    private String dateOfFlight;
   private  String bookingId;


    public Booking(String nameOfPassenger, String typeOfFlight, int classOfFlight, String dateOfFlight, String destination, String departure) {
        this.nameOfPassenger = nameOfPassenger;
        this.typeOfFlight = typeOfFlight;
        this.classOfFlight = classOfFlight;
        this.dateOfFlight = dateOfFlight;
        this.destination = destination;
        this.departure = departure;
        bookingId = setBookingCode(nameOfPassenger);

    }

    public String getTypeOfFlight() {
        return typeOfFlight;
    }

    public void setTypeOfFlight(String typeOfFlight) {
        this.typeOfFlight = typeOfFlight;
    }

    public int getClassOfFlight() {
        return classOfFlight;
    }

    public void setClassOfFlight(int classOfFlight) {
        this.classOfFlight = classOfFlight;
    }

    public String getDateOfFlight() {
        return dateOfFlight;
    }

    public void setDateOfFlight(String dateOfFlight) {
        this.dateOfFlight = dateOfFlight;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getNameOfPassenger() {
        return nameOfPassenger;
    }

    public void setNameOfPassenger(String nameOfPassenger) {
        this.nameOfPassenger = nameOfPassenger;
    }

    public String setBookingCode(String nameOfPassenger){
        CodeGenerator gen = new CodeGenerator();
        String code = gen.genBookingCode(nameOfPassenger);

        return code;
    }

    public String assignFlighttoBook(){
        Flight flight = new Flight(departure, destination);
        String code = flight.generateFlightCode(destination);
        Seat seat = new Seat(classOfFlight,code);
        if(seat.seatsAvailable()){
            return code;
        }
       return "No seat available";
    }
}
