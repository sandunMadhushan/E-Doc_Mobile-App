package com.s22010304.e_doc;

public class ApprovedAppointment {

    private String selectedDate;
    private String selectedPhase;
    private String selectedTime;
    private String selectedMode;
    private String patientUsername;
    private String loggedusername;

    public ApprovedAppointment() {
    }

    public ApprovedAppointment(String selectedDate, String selectedPhase, String selectedTime, String selectedMode, String patientName) {
        this.selectedDate = selectedDate;
        this.selectedPhase = selectedPhase;
        this.selectedTime = selectedTime;
        this.selectedMode = selectedMode;
        this.patientUsername = patientUsername;
    }

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

    public String getPatientUsername() {
        return patientUsername;
    }

    public void setPatientUsername(String patientUsername) {
        this.patientUsername = patientUsername;
    }

    public String getLoggedusername() {
        return loggedusername;
    }

    public void setLoggedusername(String loggedusername) {
        this.loggedusername = loggedusername;
    }

}
