package com.campusplacement.backend.repository;

import com.campusplacement.backend.config.DBConfig;
import com.campusplacement.backend.model.Placement;
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
public class PlacementRepository {

    public List<Placement> findAll() {
        List<Placement> list = new ArrayList<>();
        String sql = "SELECT p.*, u.name as studentName, c.company_name FROM placements p " +
                     "JOIN students s ON p.student_id=s.id JOIN users u ON s.user_id=u.id " +
                     "JOIN companies c ON p.company_id=c.id ORDER BY p.placement_date DESC";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public Placement findByStudentId(int studentId) {
        String sql = "SELECT p.*, u.name as studentName, c.company_name FROM placements p " +
                     "JOIN students s ON p.student_id=s.id JOIN users u ON s.user_id=u.id " +
                     "JOIN companies c ON p.company_id=c.id WHERE p.student_id=?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public Placement createPlacement(Placement p) {
        String sql = "INSERT INTO placements (student_id, company_id, drive_id, role, package_lpa, status) VALUES (?,?,?,?,?,'SELECTED')";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, p.getStudentId());
            ps.setInt(2, p.getCompanyId());
            ps.setInt(3, p.getDriveId());
            ps.setString(4, p.getRole());
            ps.setBigDecimal(5, p.getPackageLpa());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) p.setId(keys.getInt(1));
            return p;
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    private Placement mapRow(ResultSet rs) throws SQLException {
        Placement p = new Placement();
        p.setId(rs.getInt("id"));
        p.setStudentId(rs.getInt("student_id"));
        p.setCompanyId(rs.getInt("company_id"));
        p.setDriveId(rs.getInt("drive_id"));
        p.setRole(rs.getString("role"));
        p.setStatus(rs.getString("status"));
        p.setPackageLpa(rs.getBigDecimal("package_lpa"));
        Date d = rs.getDate("placement_date");
        if (d != null) p.setPlacementDate(d.toLocalDate());
        p.setStudentName(rs.getString("studentName"));
        p.setCompanyName(rs.getString("company_name"));
        return p;
    }
}