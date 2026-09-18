package com.campusplacement.backend.repository;

import com.campusplacement.backend.config.DBConfig;
import com.campusplacement.backend.model.Student;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepository {

    public List<Student> findAll() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT s.*, u.name, u.email FROM students s JOIN users u ON s.user_id = u.id";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public Student findById(int id) {
        String sql = "SELECT s.*, u.name, u.email FROM students s JOIN users u ON s.user_id = u.id WHERE s.id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public Student findByUserId(int userId) {
        String sql = "SELECT s.*, u.name, u.email FROM students s JOIN users u ON s.user_id = u.id WHERE s.user_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean updateStudent(int id, Student s) {
        String sql = "UPDATE students SET phone=?, department=?, batch=?, cgpa=?, tenth_percentage=?, " +
                     "twelfth_percentage=?, backlogs=?, skills=?, achievements=?, certifications=?, projects=? WHERE id=?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getPhone());
            ps.setString(2, s.getDepartment());
            ps.setInt(3, s.getBatch());
            ps.setBigDecimal(4, s.getCgpa());
            ps.setBigDecimal(5, s.getTenthPercentage());
            ps.setBigDecimal(6, s.getTwelfthPercentage());
            ps.setInt(7, s.getBacklogs());
            ps.setString(8, s.getSkills());
            ps.setString(9, s.getAchievements());
            ps.setString(10, s.getCertifications());
            ps.setString(11, s.getProjects());
            ps.setInt(12, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private Student mapRow(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setId(rs.getInt("id"));
        s.setUserId(rs.getInt("user_id"));
        s.setRegisterNo(rs.getString("register_no"));
        s.setPhone(rs.getString("phone"));
        s.setDepartment(rs.getString("department"));
        s.setBatch(rs.getInt("batch"));
        s.setCgpa(rs.getBigDecimal("cgpa"));
        s.setTenthPercentage(rs.getBigDecimal("tenth_percentage"));
        s.setTwelfthPercentage(rs.getBigDecimal("twelfth_percentage"));
        s.setBacklogs(rs.getInt("backlogs"));
        s.setSkills(rs.getString("skills"));
        s.setAchievements(rs.getString("achievements"));
        s.setCertifications(rs.getString("certifications"));
        s.setProjects(rs.getString("projects"));
        s.setName(rs.getString("name"));
        s.setEmail(rs.getString("email"));
        return s;
    }
}