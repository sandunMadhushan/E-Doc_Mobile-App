package com.s22010304.e_doc;

public class ApprovedAppointment {

    private String selectedDate;
    private String selectedTime;
    private String selectedMode;
    private String patientUsername;
    private String loggedUsername;

    public ApprovedAppointment() {}

    public ApprovedAppointment(String selectedDate, String selectedTime, String selectedMode, String patientUsername) {
        this.selectedDate = selectedDate;
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

    public String getLoggedUsername() {
        return loggedUsername;
    }

    public void setLoggedUsername(String loggedUsername) {
        this.loggedUsername = loggedUsername;
    }
}
