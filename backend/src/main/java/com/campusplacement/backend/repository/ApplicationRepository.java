package com.campusplacement.backend.repository;

import com.campusplacement.backend.config.DBConfig;
import com.campusplacement.backend.model.Application;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ApplicationRepository {

    public List<Application> findAll() {
        List<Application> apps = new ArrayList<>();
        String sql = "SELECT a.*, s.register_no as studentName, d.role, d.package_lpa, c.company_name " +
                     "FROM applications a JOIN students s ON a.student_id=s.id " +
                     "JOIN drives d ON a.drive_id=d.id JOIN companies c ON d.company_id=c.id ORDER BY a.applied_date DESC";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Application a = mapRow(rs);
                a.setStudentName(rs.getString("studentName"));
                apps.add(a);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return apps;
    }

    public List<Application> findByStudentId(int studentId) {
        List<Application> apps = new ArrayList<>();
        String sql = "SELECT a.*, d.role, d.package_lpa, c.company_name FROM applications a " +
                     "JOIN drives d ON a.drive_id=d.id JOIN companies c ON d.company_id=c.id " +
                     "WHERE a.student_id=? ORDER BY a.applied_date DESC";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) apps.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return apps;
    }

    public List<Application> findByDriveId(int driveId) {
        List<Application> apps = new ArrayList<>();
        String sql = "SELECT a.*, s.register_no as studentName, d.role, d.package_lpa, c.company_name " +
                     "FROM applications a JOIN students s ON a.student_id=s.id " +
                     "JOIN drives d ON a.drive_id=d.id JOIN companies c ON d.company_id=c.id " +
                     "WHERE a.drive_id=? ORDER BY a.applied_date DESC";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, driveId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Application a = mapRow(rs);
                a.setStudentName(rs.getString("studentName"));
                apps.add(a);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return apps;
    }

    public boolean exists(int studentId, int driveId) {
        String sql = "SELECT id FROM applications WHERE student_id=? AND drive_id=?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, driveId);
            return ps.executeQuery().next();
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public Application createApplication(int studentId, int driveId) {
        String sql = "INSERT INTO applications (student_id, drive_id, status) VALUES (?, ?, 'APPLIED')";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, studentId);
            ps.setInt(2, driveId);
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            Application a = new Application();
            a.setStudentId(studentId);
            a.setDriveId(driveId);
            a.setStatus("APPLIED");
            if (keys.next()) a.setId(keys.getInt(1));
            return a;
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE applications SET status=? WHERE id=?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public Application findById(int id) {
        String sql = "SELECT a.*, d.role, d.package_lpa, c.company_name FROM applications a " +
                     "JOIN drives d ON a.drive_id=d.id JOIN companies c ON d.company_id=c.id WHERE a.id=?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    private Application mapRow(ResultSet rs) throws SQLException {
        Application a = new Application();
        a.setId(rs.getInt("id"));
        a.setStudentId(rs.getInt("student_id"));
        a.setDriveId(rs.getInt("drive_id"));
        a.setStatus(rs.getString("status"));
        a.setAppliedDate(rs.getTimestamp("applied_date"));
        a.setRole(rs.getString("role"));
        a.setPackageLpa(rs.getBigDecimal("package_lpa"));
        a.setCompanyName(rs.getString("company_name"));
        return a;
    }
}