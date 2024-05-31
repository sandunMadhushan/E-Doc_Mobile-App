package com.s22010304.e_doc;

public class Appointment {
    private String selectedDate;
    private String selectedPhase;
    private String selectedTime;
    private String selectedMode;
    private String doctorName;
    private String specialArea;
    private String username;
    private String loggedusername;
    private String status;

    public Appointment() {
        // Default constructor required for Firebase
    }

    public Appointment(String selectedDate, String selectedPhase, String selectedTime, String selectedMode, String status) {
        this.selectedDate = selectedDate;
        this.selectedPhase = selectedPhase;
        this.selectedTime = selectedTime;
        this.selectedMode = selectedMode;
        this.status = status;
    }

    // Getters and setters
    public String getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    public String getSelectedPhase() {
        return selectedPhase;
    }

    public void setSelectedPhase(String selectedPhase) {
        this.selectedPhase = selectedPhase;
    }

    public String getSelectedTime() {
        return selectedTime;
    }

    public void setSelectedTime(String selectedTime) {
        this.selectedTime = selectedTime;
    }

    public String getSelectedMode() {
        return selectedMode;
    }

    public void setSelectedMode(String selectedMode) {
        this.selectedMode = selectedMode;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getSpecialArea() {
        return specialArea;
    }

    public void setSpecialArea(String specialArea) {
        this.specialArea = specialArea;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getloggedusername() {
        return loggedusername;
    }

    public void setloggedusername(String loggedusername) {
        this.loggedusername = loggedusername;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
