package com.campusplacement.backend.controller;

import com.campusplacement.backend.config.DBConfig;
import org.springframework.web.bind.annotation.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:5173")
public class DashboardController {

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        try (Connection conn = DBConfig.getConnection()) {
            stats.put("students", count(conn, "SELECT COUNT(*) FROM students"));
            stats.put("companies", count(conn, "SELECT COUNT(*) FROM companies"));
            stats.put("activeDrives", count(conn, "SELECT COUNT(*) FROM drives WHERE status='OPEN'"));
            stats.put("applications", count(conn, "SELECT COUNT(*) FROM applications"));
            stats.put("placed", count(conn, "SELECT COUNT(*) FROM placements"));
        } catch (SQLException e) { e.printStackTrace(); }
        return stats;
    }

    private int count(Connection conn, String sql) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }
}