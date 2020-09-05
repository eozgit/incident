package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class IncidentDb {
    private final String url = "jdbc:postgresql://incident-database-2.cbxuuegomn9j.us-east-1.rds.amazonaws.com/info";
    private final String user = "postgres";
    private final String password = "ENZy2Br9hGkG52ztqpg3";

    public int insertIncident(Incident incident) {
        String incidentSQL = "INSERT INTO public.incident (reported_to, \"location\", incident_date, reported_by, nature, detail) VALUES(?, ?, ?, ?, ?, ?);";
        String personSQL = "INSERT INTO public.person (\"name\", category, incident_id, side) VALUES(?, ?, ?, ?);";

        int id = 0;

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement psIncident = conn.prepareStatement(incidentSQL, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement psPersons = conn.prepareStatement(personSQL, Statement.RETURN_GENERATED_KEYS)) {

            psIncident.setString(1, incident.getReportedTo());
            psIncident.setString(2, incident.getLocation());
            psIncident.setTimestamp(3, incident.getIncidentDate());
            psIncident.setString(4, incident.getReportedBy());
            psIncident.setString(5, incident.getNature());
            psIncident.setString(6, incident.getDetail());

            int affectedRows = psIncident.executeUpdate();

            if (affectedRows > 0) {

                try (ResultSet rs = psIncident.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getInt(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }

                for (Person person : incident.getParties()) {
                    psPersons.setString(1, person.getName());
                    psPersons.setString(2, person.getCategory());
                    psPersons.setInt(3, id);
                    psPersons.setString(4, person.getSide());
                    psPersons.executeUpdate();
                }
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }

    public void updateIncident(Incident incident) {
        String incidentSQL = "UPDATE public.incident SET reviewer=?, complete_date=?, experiencing_concerns_listened_to=?, experiencing_satisfied=?, displaying_concerns_listened_to=?, displaying_satisfied=?, \"procedures\"=?, conclusion=? WHERE id=?;";
        String personSQL = "UPDATE public.person SET \"action\"=? WHERE id=?;";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement incidentPs = conn.prepareStatement(incidentSQL, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement personPs = conn.prepareStatement(personSQL, Statement.RETURN_GENERATED_KEYS)) {

            incidentPs.setString(1, incident.getReviewer());
            incidentPs.setDate(2, incident.getCompleteDate());
            incidentPs.setBoolean(3, incident.isExperiencingConcernsListenedTo());
            incidentPs.setBoolean(4, incident.isExperiencingSatisfied());
            incidentPs.setBoolean(5, incident.isDisplayingConcernsListenedTo());
            incidentPs.setBoolean(6, incident.isDisplayingSatisfied());
            incidentPs.setString(7, incident.getProcedures());
            incidentPs.setString(8, incident.getConclusion());
            incidentPs.setInt(9, incident.getId());

            incidentPs.executeUpdate();

            for (Person person : incident.getParties()) {
                personPs.setString(1, person.getAction());
                personPs.setInt(2, person.getId());

                personPs.executeUpdate();
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Incident> getIncidents() {
        String incidentsSQL = "SELECT id, reported_to, \"location\", incident_date, reported_by, nature, detail, reviewer, complete_date, experiencing_concerns_listened_to, experiencing_satisfied, displaying_concerns_listened_to, displaying_satisfied, \"procedures\", conclusion FROM public.incident;";
        String personsSQL = "SELECT id, name, category, \"action\", incident_id, side FROM public.person;";

        List<Incident> incidents = new LinkedList<Incident>();
        List<Person> persons = new LinkedList<Person>();

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement psIncidents = conn.prepareStatement(incidentsSQL, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement psPersons = conn.prepareStatement(personsSQL, Statement.RETURN_GENERATED_KEYS)) {

            ResultSet rs = psIncidents.executeQuery();

            while (rs.next()) {
                Incident incident = new Incident();
                incident.setId(rs.getInt("id"));
                incident.setReportedTo(rs.getString("reported_to"));
                incident.setLocation(rs.getString("location"));
                incident.setIncidentDate(rs.getTimestamp("incident_date"));
                incident.setReportedBy(rs.getString("reported_by"));
                incident.setNature(rs.getString("nature"));
                incident.setDetail(rs.getString("detail"));
                incident.setReviewer(rs.getString("reviewer"));
                incident.setCompleteDate(rs.getDate("complete_date"));
                incident.setExperiencingConcernsListenedTo(rs.getBoolean("experiencing_concerns_listened_to"));
                incident.setExperiencingSatisfied(rs.getBoolean("experiencing_satisfied"));
                incident.setDisplayingConcernsListenedTo(rs.getBoolean("displaying_concerns_listened_to"));
                incident.setDisplayingSatisfied(rs.getBoolean("displaying_satisfied"));
                incident.setProcedures(rs.getString("procedures"));
                incident.setConclusion(rs.getString("conclusion"));
                incidents.add(incident);
            }
            rs.close();

            rs = psPersons.executeQuery();

            while (rs.next()) {
                Person person = new Person();
                person.setId(rs.getInt("id"));
                person.setName(rs.getString("name"));
                person.setCategory(rs.getString("category"));
                person.setAction(rs.getString("action"));
                person.setIncidentId(rs.getInt("incident_id"));
                person.setSide(rs.getString("side"));
                persons.add(person);
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        Match(incidents, persons);

        return incidents;
    }

    private void Match(List<Incident> incidents, List<Person> persons) {
        for (Person person : persons) {
            Incident incident = GetIncidentById(incidents, person.getIncidentId());
            if (incident != null) {

                List<Person> parties = incident.getParties();
                parties.add(person);
                incident.setParties(parties);
            }
        }
    }

    private Incident GetIncidentById(List<Incident> incidents, int incidentId) {
        for (Incident incident : incidents) {
            if (incident.getId() == incidentId) {
                return incident;
            }
        }
        return null;
    }
}
