package com.example;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class ExampleResource implements RequestHandler<InputObject, OutputObject> {

    @Override
    public OutputObject handleRequest(InputObject input, Context context) {
        IncidentDb db = new IncidentDb();
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
