package com.campusplacement.backend.repository;

import com.campusplacement.backend.config.DBConfig;
import com.campusplacement.backend.model.RoundResult;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoundResultRepository {

    public List<RoundResult> findByApplicationId(int appId) {
        List<RoundResult> results = new ArrayList<>();
        String sql = "SELECT rr.*, r.round_name FROM round_results rr JOIN rounds r ON rr.round_id=r.id WHERE rr.application_id=? ORDER BY r.round_number";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) results.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return results;
    }

    public RoundResult createOrUpdate(RoundResult rr) {
        String sql = "INSERT INTO round_results (application_id, round_id, marks, result, remarks) VALUES (?, ?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE marks=?, result=?, remarks=?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, rr.getApplicationId());
            ps.setInt(2, rr.getRoundId());
            ps.setBigDecimal(3, rr.getMarks());
            ps.setString(4, rr.getResult());
            ps.setString(5, rr.getRemarks());
            ps.setBigDecimal(6, rr.getMarks());
            ps.setString(7, rr.getResult());
            ps.setString(8, rr.getRemarks());
            ps.executeUpdate();
            return rr;
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    private RoundResult mapRow(ResultSet rs) throws SQLException {
        RoundResult r = new RoundResult();
        r.setId(rs.getInt("id"));
        r.setApplicationId(rs.getInt("application_id"));
        r.setRoundId(rs.getInt("round_id"));
        r.setMarks(rs.getBigDecimal("marks"));
        r.setResult(rs.getString("result"));
        r.setRemarks(rs.getString("remarks"));
        r.setRoundName(rs.getString("round_name"));
        return r;
    }
}