package com.campusplacement.backend.repository;

import com.campusplacement.backend.config.DBConfig;
import com.campusplacement.backend.model.User;
import org.springframework.stereotype.Repository;
import java.sql.*;

@Repository
public class UserRepository {

    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"),
                        rs.getString("password"), rs.getString("role"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean emailExists(String email) { return findByEmail(email) != null; }

    public User createUser(String name, String email, String password, String role) {
        String sql = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name); ps.setString(2, email); ps.setString(3, password); ps.setString(4, role);
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            int newId = 0;
            if (keys.next()) newId = keys.getInt(1);
            return new User(newId, name, email, password, role);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}