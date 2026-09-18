package com.campusplacement.backend.repository;

import com.campusplacement.backend.config.DBConfig;
import com.campusplacement.backend.model.Company;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {

    public List<Company> findAll() {
        List<Company> companies = new ArrayList<>();
        String sql = "SELECT c.*, u.email FROM companies c JOIN users u ON c.user_id = u.id";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) companies.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return companies;
    }

    public Company findById(int id) {
        String sql = "SELECT c.*, u.email FROM companies c JOIN users u ON c.user_id = u.id WHERE c.id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public Company findByUserId(int userId) {
        String sql = "SELECT c.*, u.email FROM companies c JOIN users u ON c.user_id = u.id WHERE c.user_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean updateCompany(int id, Company c) {
        String sql = "UPDATE companies SET company_name=?, industry=?, location=?, website=?, description=? WHERE id=?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getCompanyName());
            ps.setString(2, c.getIndustry());
            ps.setString(3, c.getLocation());
            ps.setString(4, c.getWebsite());
            ps.setString(5, c.getDescription());
            ps.setInt(6, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private Company mapRow(ResultSet rs) throws SQLException {
        Company c = new Company();
        c.setId(rs.getInt("id"));
        c.setUserId(rs.getInt("user_id"));
        c.setCompanyName(rs.getString("company_name"));
        c.setIndustry(rs.getString("industry"));
        c.setLocation(rs.getString("location"));
        c.setWebsite(rs.getString("website"));
        c.setDescription(rs.getString("description"));
        c.setEmail(rs.getString("email"));
        return c;
    }
}