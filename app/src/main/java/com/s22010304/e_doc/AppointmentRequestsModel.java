package com.s22010304.e_doc;

public class AppointmentRequestsModel {

    private String selectedDate;
    private String selectedTime;
    private String selectedMode;
    private String loggedusername;
    private String status;

    public AppointmentRequestsModel() {
    }

    public AppointmentRequestsModel(String selectedDate, String selectedTime, String selectedMode, String loggedusername) {
        this.selectedDate = selectedDate;
        this.selectedTime = selectedTime;
        this.selectedMode = selectedMode;
        this.loggedusername = loggedusername;
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

    public String getLoggedusername() {
        return loggedusername;
    }

    public void setLoggedusername(String loggedusername) {
        this.loggedusername = loggedusername;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
