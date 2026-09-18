package com.campusplacement.backend.repository;

import com.campusplacement.backend.config.DBConfig;
import com.campusplacement.backend.model.Drive;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DriveRepository {

    public List<Drive> findAll() {
        List<Drive> drives = new ArrayList<>();
        String sql = "SELECT d.*, c.company_name FROM drives d JOIN companies c ON d.company_id = c.id ORDER BY d.created_at DESC";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) drives.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return drives;
    }

    public List<Drive> findByCompanyId(int companyId) {
        List<Drive> drives = new ArrayList<>();
        String sql = "SELECT d.*, c.company_name FROM drives d JOIN companies c ON d.company_id = c.id WHERE d.company_id = ? ORDER BY d.created_at DESC";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, companyId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) drives.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return drives;
    }

    public Drive findById(int id) {
        String sql = "SELECT d.*, c.company_name FROM drives d JOIN companies c ON d.company_id = c.id WHERE d.id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public Drive createDrive(Drive d) {
        String sql = "INSERT INTO drives (company_id, role, package_lpa, location, description, min_cgpa, max_backlogs, allowed_departments, passing_year, application_deadline, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, d.getCompanyId());
            ps.setString(2, d.getRole());
            ps.setBigDecimal(3, d.getPackageLpa());
            ps.setString(4, d.getLocation());
            ps.setString(5, d.getDescription());
            ps.setBigDecimal(6, d.getMinCgpa());
            ps.setInt(7, d.getMaxBacklogs());
            ps.setString(8, d.getAllowedDepartments());
            ps.setInt(9, d.getPassingYear());
            ps.setDate(10, Date.valueOf(d.getApplicationDeadline()));
            ps.setString(11, d.getStatus() != null ? d.getStatus() : "OPEN");
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) d.setId(keys.getInt(1));
            return d;
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean updateDrive(int id, Drive d) {
        String sql = "UPDATE drives SET role=?, package_lpa=?, location=?, description=?, min_cgpa=?, max_backlogs=?, allowed_departments=?, passing_year=?, application_deadline=?, status=? WHERE id=?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getRole());
            ps.setBigDecimal(2, d.getPackageLpa());
            ps.setString(3, d.getLocation());
            ps.setString(4, d.getDescription());
            ps.setBigDecimal(5, d.getMinCgpa());
            ps.setInt(6, d.getMaxBacklogs());
            ps.setString(7, d.getAllowedDepartments());
            ps.setInt(8, d.getPassingYear());
            ps.setDate(9, Date.valueOf(d.getApplicationDeadline()));
            ps.setString(10, d.getStatus());
            ps.setInt(11, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private Drive mapRow(ResultSet rs) throws SQLException {
        Drive d = new Drive();
        d.setId(rs.getInt("id"));
        d.setCompanyId(rs.getInt("company_id"));
        d.setRole(rs.getString("role"));
        d.setPackageLpa(rs.getBigDecimal("package_lpa"));
        d.setLocation(rs.getString("location"));
        d.setDescription(rs.getString("description"));
        d.setMinCgpa(rs.getBigDecimal("min_cgpa"));
        d.setMaxBacklogs(rs.getInt("max_backlogs"));
        d.setAllowedDepartments(rs.getString("allowed_departments"));
        d.setPassingYear(rs.getInt("passing_year"));
        Date deadline = rs.getDate("application_deadline");
        if (deadline != null) d.setApplicationDeadline(deadline.toLocalDate());
        d.setStatus(rs.getString("status"));
        d.setCompanyName(rs.getString("company_name"));
        return d;
    }
}