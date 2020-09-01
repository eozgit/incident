package com.example;

public class InputObject {

    private String method;
    private Incident incident;

    public String getMethod() {
        return method;
    }

    public InputObject setMethod(String method) {
        this.method = method;
        return this;
    }

    public Incident getIncident() {
        return incident;
    }

    public InputObject setIncident(Incident incident) {
        this.incident = incident;
        return this;
    }
}
