package com.campusplacement.backend.repository;

import com.campusplacement.backend.config.DBConfig;
import com.campusplacement.backend.model.Round;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoundRepository {

    public List<Round> findByDriveId(int driveId) {
        List<Round> rounds = new ArrayList<>();
        String sql = "SELECT * FROM rounds WHERE drive_id=? ORDER BY round_number";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, driveId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) rounds.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return rounds;
    }

    public Round findById(int id) {
        String sql = "SELECT * FROM rounds WHERE id=?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public Round createRound(Round r) {
        String sql = "INSERT INTO rounds (drive_id, round_name, round_number, round_date, description, mode, location) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, r.getDriveId());
            ps.setString(2, r.getRoundName());
            ps.setInt(3, r.getRoundNumber());
            ps.setDate(4, Date.valueOf(r.getRoundDate()));
            ps.setString(5, r.getDescription());
            ps.setString(6, r.getMode());
            ps.setString(7, r.getLocation());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) r.setId(keys.getInt(1));
            return r;
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean deleteRound(int id) {
        String sql = "DELETE FROM rounds WHERE id=?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private Round mapRow(ResultSet rs) throws SQLException {
        Round r = new Round();
        r.setId(rs.getInt("id"));
        r.setDriveId(rs.getInt("drive_id"));
        r.setRoundName(rs.getString("round_name"));
        r.setRoundNumber(rs.getInt("round_number"));
        Date d = rs.getDate("round_date");
        if (d != null) r.setRoundDate(d.toLocalDate());
        r.setDescription(rs.getString("description"));
        r.setMode(rs.getString("mode"));
        r.setLocation(rs.getString("location"));
        return r;
    }
}