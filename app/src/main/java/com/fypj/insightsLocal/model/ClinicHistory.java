package com.fypj.insightsLocal.model;

/**
 * Created by L33525 on 26/9/2014.
 */
public class ClinicHistory {
       private String clinicName;
       private String service;
       private float amount;

    public ClinicHistory(String clinicName, String service, float amount) {
        this.clinicName = clinicName;
        this.service = service;
        this.amount = amount;
    }

    public String getClinicName() {

        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
