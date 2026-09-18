package com.campusplacement.backend.repository;

import com.campusplacement.backend.config.DBConfig;
import com.campusplacement.backend.model.Interview;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InterviewRepository {

    public List<Interview> findByStudentId(int studentId) {
        List<Interview> list = new ArrayList<>();
        String sql = "SELECT i.*, r.round_name FROM interviews i JOIN applications a ON i.application_id=a.id " +
                     "JOIN rounds r ON i.round_id=r.id WHERE a.student_id=? ORDER BY i.interview_date";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public Interview createInterview(Interview i) {
        String sql = "INSERT INTO interviews (application_id, round_id, interview_date, interview_time, mode, location, interviewer, meeting_link, remarks) VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, i.getApplicationId());
            ps.setInt(2, i.getRoundId());
            ps.setDate(3, Date.valueOf(i.getInterviewDate()));
            ps.setTime(4, Time.valueOf(i.getInterviewTime()));
            ps.setString(5, i.getMode());
            ps.setString(6, i.getLocation());
            ps.setString(7, i.getInterviewer());
            ps.setString(8, i.getMeetingLink());
            ps.setString(9, i.getRemarks());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) i.setId(keys.getInt(1));
            return i;
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    private Interview mapRow(ResultSet rs) throws SQLException {
        Interview i = new Interview();
        i.setId(rs.getInt("id"));
        i.setApplicationId(rs.getInt("application_id"));
        i.setRoundId(rs.getInt("round_id"));
        Date d = rs.getDate("interview_date");
        if (d != null) i.setInterviewDate(d.toLocalDate());
        Time t = rs.getTime("interview_time");
        if (t != null) i.setInterviewTime(t.toLocalTime());
        i.setMode(rs.getString("mode"));
        i.setLocation(rs.getString("location"));
        i.setInterviewer(rs.getString("interviewer"));
        i.setMeetingLink(rs.getString("meeting_link"));
        i.setRemarks(rs.getString("remarks"));
        i.setRoundName(rs.getString("round_name"));
        return i;
    }
}