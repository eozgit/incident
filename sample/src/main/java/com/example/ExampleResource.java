package com.example;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.eclipse.microprofile.config.inject.ConfigProperty;

public class ExampleResource implements RequestHandler<InputObject, OutputObject> {

    @ConfigProperty(name = "quarkus.datasource.jdbc.url")
    public String url;
    @ConfigProperty(name = "quarkus.datasource.username")
    public String user;
    @ConfigProperty(name = "quarkus.datasource.password")
    public String password;

    @Override
    public OutputObject handleRequest(InputObject input, Context context) {
        IncidentDb db = new IncidentDb(url, user, password);
        switch (input.getMethod()) {
            case "add":
                db.insertIncident(input.getIncident());
                break;
            case "update":
                db.updateIncident(input.getIncident());
                break;
        }

        List<Incident> incidents = db.getIncidents();

        OutputObject output = new OutputObject();
        output.setIncidents(incidents);
        return output;
    }
}
