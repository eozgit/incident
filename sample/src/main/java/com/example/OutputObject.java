package com.example;

import java.util.List;

public class OutputObject {

    private List<Incident> incidents;

    public List<Incident> getIncidents() {
        return incidents;
    }

    public OutputObject setIncidents(List<Incident> incidents) {
        this.incidents = incidents;
        return this;
    }
}
