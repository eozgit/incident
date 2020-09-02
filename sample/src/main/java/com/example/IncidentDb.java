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
        String SQL = "INSERT INTO public.incident (reported_to, \"location\", incident_date, reported_by, nature, detail) VALUES(?, ?, ?, ?, ?, ?);";

        int id = 0;

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, incident.getReportedTo());
            pstmt.setString(2, incident.getLocation());
            pstmt.setTimestamp(3, incident.getIncidentDate());
            pstmt.setString(4, incident.getReportedBy());
            pstmt.setString(5, incident.getNature());
            pstmt.setString(6, incident.getDetail());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {

                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getInt(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }

    public void updateIncident(Incident incident) {
        String SQL = "UPDATE public.incident SET reviewer=?, complete_date=?, experiencing_concerns_listened_to=?, experiencing_satisfied=?, displaying_concerns_listened_to=?, displaying_satisfied=?, \"procedures\"=?, conclusion=? WHERE id=?;";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, incident.getReviewer());
            pstmt.setDate(2, incident.getCompleteDate());
            pstmt.setBoolean(3, incident.isExperiencingConcernsListenedTo());
            pstmt.setBoolean(4, incident.isExperiencingSatisfied());
            pstmt.setBoolean(5, incident.isDisplayingConcernsListenedTo());
            pstmt.setBoolean(6, incident.isDisplayingSatisfied());
            pstmt.setString(7, incident.getProcedures());
            pstmt.setInt(8, incident.getConclusion());
            pstmt.setInt(9, incident.getId());

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Incident> getIncidents() {
        String SQL = "SELECT id, reported_to, \"location\", incident_date, reported_by, nature, detail, reviewer, complete_date, experiencing_concerns_listened_to, experiencing_satisfied, displaying_concerns_listened_to, displaying_satisfied, \"procedures\", conclusion FROM public.incident;";

        List<Incident> incidents = new LinkedList<Incident>();

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Incident incident = new Incident();
                incident.setId(rs.getInt("id"));
                incident.setReportedTo(rs.getString("reported_to"));
                incident.setLocation(rs.getString("location"));
                incident.setIncidentDate(rs.getTimestamp("incident_date"));
                incident.setReportedBy(rs.getString("reported_by"));
                incident.setNature(rs.getString("nature"));
                incident.setDetail(rs.getString("detail"));
                incidents.add(incident);
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return incidents;
    }
}
